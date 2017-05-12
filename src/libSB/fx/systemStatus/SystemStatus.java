/*
 * The MIT License
 *
 * Copyright 2015 Simon Berndt.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package libSB.fx.systemStatus;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import libSB.concurrent.DaemonThreadFactory;
import libSB.fx.pollService.PollingJob;
import libSB.misc.OptionalCasting;
import libSB.misc.OptionalReflection;

/**
 *
 * @author Simon Berndt
 */
public enum SystemStatus {
    
    INSTANCE;

    private static final Logger LOG = Logger.getLogger(SystemStatus.class.getName());

    private final LongProperty heapMemoryCommited;
    private final LongProperty heapMemoryUsed;
    private final LongProperty ofHeapMemoryUsed;
    private final DoubleProperty systemLoadAverage;

    private final Collection<PollingJob<?>> pollingJobs;

    private static final String HACK_CLASS_NAME = ScheduledThreadPoolExecutor.class.getName() + "$ScheduledFutureTask";
    private static final String HACK_FIELD_NAME = "period";

    private final boolean updateChangeSupport;

    private final ScheduledFuture<?> scheduledJob;

    private SystemStatus() {
        this.pollingJobs = new ArrayList<>();
        this.systemLoadAverage = new SimpleDoubleProperty(this, "systemLoadAverage", 0);
        this.heapMemoryUsed = new SimpleLongProperty(this, "heapMemoryUsed", 0);
        this.heapMemoryCommited = new SimpleLongProperty(this, "heapMemoryComitted", 0);
        this.ofHeapMemoryUsed = new SimpleLongProperty(this, "nonHeapMemoryUsed", 0);

        final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        OptionalCasting.cast(ManagementFactory.getOperatingSystemMXBean(), OperatingSystemMXBean.class).ifPresent((OperatingSystemMXBean operatingSystemMXBean) -> {
            pollingJobs.add(new PollingJob<>(operatingSystemMXBean::getSystemCpuLoad, systemLoadAverage::set));
        });

        pollingJobs.add(new PollingJob<>(memoryMXBean.getHeapMemoryUsage()::getUsed, heapMemoryUsed::set));
        pollingJobs.add(new PollingJob<>(memoryMXBean.getHeapMemoryUsage()::getCommitted, heapMemoryCommited::set));
        pollingJobs.add(new PollingJob<>(memoryMXBean.getNonHeapMemoryUsage()::getUsed, ofHeapMemoryUsed::set));

        Optional<Field> periodField = getHackField().flatMap(OptionalReflection::makeWritable);

        this.scheduledJob = Executors.newSingleThreadScheduledExecutor(DaemonThreadFactory::createThread).scheduleAtFixedRate(this::updateProperties, 0, 1, TimeUnit.SECONDS);

        boolean changeSupport = false;
        if (periodField.isPresent()) {
            final Field field = periodField.get();
            try {
                long lastValue = field.getLong(scheduledJob);
                field.setLong(scheduledJob, lastValue + 1);
                changeSupport = true;
            } catch (IllegalArgumentException | IllegalAccessException ex) {
//                LOG.log(Level.INFO, "Polling-Rate is fixed");
            }
        }
        this.updateChangeSupport = changeSupport;
    }

    private Optional<Field> getHackField() {
        Optional<Class<?>> scheduledFutureTaskClass = OptionalReflection.getClass(HACK_CLASS_NAME);
        if (scheduledFutureTaskClass.isPresent()) {
            return OptionalReflection.getField(scheduledFutureTaskClass.get(), HACK_FIELD_NAME);
        }
        return Optional.empty();
    }

    public boolean setPollingRate(TimeUnit unit, long duration) {
        long nanos = unit.toNanos(duration);
        if (updateChangeSupport) {
            Optional<Field> periodField = getHackField();
            if (periodField.isPresent()) {
                final Field field = periodField.get();
                try {
                    field.setAccessible(true);
                    field.setLong(scheduledJob, nanos);
                    LOG.log(Level.INFO, "Set Polling-Rate to {0}ns", nanos);
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    LOG.log(Level.INFO, "Polling-Rate is fixed");
                }
            }
        }
        return false;
    }

    public boolean getPollingRateChangeSupport() {
        return updateChangeSupport;
    }

    public ReadOnlyDoubleProperty systemLoadAverageProperty() {
        return systemLoadAverage;
    }

    public double getSystemLoadAverage() {
        return systemLoadAverage.get();
    }

    private void updateProperties() {
        pollingJobs.forEach(PollingJob::run);
    }

    public ReadOnlyLongProperty heapMemoryUsedProperty() {
        return heapMemoryUsed;
    }

    public long getHeapMemoryUsed() {
        return heapMemoryUsed.get();
    }

    public ReadOnlyLongProperty heapMemoryCommitedProperty() {
        return heapMemoryCommited;
    }

    public long getHeapMemoryCommited() {
        return heapMemoryCommited.get();
    }

    public ReadOnlyLongProperty ofHeapMemoryUsedProperty() {
        return ofHeapMemoryUsed;
    }

    public long getOfHeapMemoryUsed() {
        return ofHeapMemoryUsed.get();
    }
}
