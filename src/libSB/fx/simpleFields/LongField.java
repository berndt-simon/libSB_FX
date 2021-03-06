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
package libSB.fx.simpleFields;

import javafx.beans.value.WritableLongValue;

/**
 *
 * @author Simon Berndt
 */
public final class LongField implements WritableLongValue {

    private long value;

    public LongField() {
        this.value = 0;
    }

    public LongField(long value) {
	this.value = value;
    }

    public static void increment(WritableLongValue l) {
	l.set(l.get() + 1);
    }

    public static void decrement(WritableLongValue l) {
	l.set(l.get() - 1);
    }

    @Override
    public long get() {
	return this.value;
    }

    @Override
    public void set(long value) {
	this.value = value;
    }

    @Override
    public void setValue(Number value) {
	this.value = value.longValue();
    }

    @Override
    public Number getValue() {
	return this.value;
    }

}
