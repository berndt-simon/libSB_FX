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
import javafx.beans.value.ObservableFloatValue;

/**
 *
 * @author Simon Berndt
 */
public final class ConstantFloat implements ObservableFloatValue {

    private final float value;

    public ConstantFloat(float value) {
	this.value = value;
    }

    @Override
    public float get() {
	return this.value;
    }

    @Override
    public double doubleValue() {
	return this.value;
    }

    @Override
    public int intValue() {
	return (int) this.value;
    }

    @Override
    public long longValue() {
	return (long) this.value;
    }

    @Override
    public float floatValue() {
	return this.value;
    }

    @Override
    public void addListener(ChangeListener<? super Number> listener) {
	// no op
    }

    @Override
    public void removeListener(ChangeListener<? super Number> listener) {
	// no op
    }

    @Override
    public Number getValue() {
	return this.value;
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
