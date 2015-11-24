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
package libSB.fx.observableConstants;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;

/**
 *
 * @author Simon Berndt
 */
public final class ConstantBoolean implements ObservableBooleanValue {

    public static final ObservableBooleanValue TRUE = new ConstantBoolean(true);
    public static final ObservableBooleanValue FALSE = new ConstantBoolean(false);

    private final boolean constant;

    private ConstantBoolean(boolean constant) {
	this.constant = constant;
    }

    @Override
    public boolean get() {
	return this.constant;
    }

    @Override
    public void addListener(ChangeListener<? super Boolean> listener) {
	// no op
    }

    @Override
    public void removeListener(ChangeListener<? super Boolean> listener) {
	// no op
    }

    @Override
    public Boolean getValue() {
	return this.constant;
    }

    @Override
    public void addListener(InvalidationListener listener) {
	// no op
    }

    @Override
    public void removeListener(InvalidationListener listener) {
	// no op
    }

}
