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
package libSB.fx.fieldAdapter;

import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.beans.value.WritableObjectValue;

/**
 *
 * @author Simon Berndt
 */
public final class ObjectAdapter<T> implements WritableObjectValue<T> {

    private final Supplier<? extends T> getter;
    private final Consumer<? super T> setter;

    public ObjectAdapter(Supplier<? extends T> getter, Consumer<? super T> setter) {
	this.getter = getter;
	this.setter = setter;
    }

    public static <T> boolean isNotNull(WritableObjectValue<T> objectValue) {
	return objectValue.get() != null;
    }

    public static <T> boolean isNull(WritableObjectValue<T> objectValue) {
	return objectValue.getValue() == null;
    }

    public boolean isNotNull() {
	return this.getter.get() != null;
    }

    public boolean isNull() {
	return this.getter.get() == null;
    }

    @Override
    public T get() {
	return this.getter.get();
    }

    @Override
    public void set(T value) {
        this.setter.accept(value);
    }

    @Override
    public T getValue() {
	return this.getter.get();
    }

    @Override
    public void setValue(T value) {
        this.setter.accept(value);
    }

}
