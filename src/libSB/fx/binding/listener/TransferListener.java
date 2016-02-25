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
package libSB.fx.binding.listener;

import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

/**
 *
 * @author Simon Berndt
 */
public final class TransferListener<T> implements InvalidationListener {

    private final Supplier<? extends T> o1Getter;
    private final Consumer<? super T> o2Setter;

    public TransferListener(Supplier<? extends T> o1Getter, Consumer<? super T> o2Setter) {
	this.o1Getter = o1Getter;
	this.o2Setter = o2Setter;
    }

    @Override
    public void invalidated(Observable observable) {
	transfer();
    }

    public void transfer() {
        this.o2Setter.accept(this.o1Getter.get());
    }

}
