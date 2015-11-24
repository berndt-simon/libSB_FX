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
package libSB.fx.binding;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;

/**
 *
 * @author Simon Berndt
 */
public final class BindingUtil {

    private BindingUtil() {
    }

    public static <T> BiDirectionalBinding<T> createBiDirectionalBinding(
	    Observable o1, Supplier<? extends T> o1Getter, Consumer<? super T> o1Setter,
	    Observable o2, Supplier<? extends T> o2Getter, Consumer<? super T> o2Setter) {
	return new BiDirectionalBinding<>(o1, o1Getter, o1Setter, o2, o2Getter, o2Setter);
    }
    
    public static <T> BiDirectionalBinding<T> createBiDirectionalBinding(
	    Property<T> o1, Observable o2, Supplier<? extends T> o2Getter, Consumer<? super T> o2Setter) {
	return new BiDirectionalBinding<>(o1, o2, o2Getter, o2Setter);
    }
    
    public static <T> BiDirectionalBinding<T> createBiDirectionalBinding(
	    Observable o1, Supplier<? extends T> o1Getter, Consumer<? super T> o1Setter, Property<T> o2) {
	return new BiDirectionalBinding<>(o1, o1Getter, o1Setter, o2);
    }

    public static <T1, T2> BiDirectionalMappingBinding<T1, T2> createBiDirectionalMappingBinding(
	    Observable o1, Supplier<? extends T1> o1Getter, Function<? super T1, ? extends T2> o1ToO2Converter, Consumer<? super T1> o1Setter,
	    Observable o2, Supplier<? extends T2> o2Getter, Function<? super T2, ? extends T1> o2ToO1Converter, Consumer<? super T2> o2Setter) {
	return new BiDirectionalMappingBinding<>(o1, o1Getter, o1ToO2Converter, o1Setter, o2, o2Getter, o2ToO1Converter, o2Setter);
    }
    
    public static <T1, T2> BiDirectionalMappingBinding<T1, T2> createBiDirectionalMappingBinding(
	    Property<T1> o1, Function<? super T1, ? extends T2> o1ToO2Converter,
	    Observable o2, Supplier<? extends T2> o2Getter, Function<? super T2, ? extends T1> o2ToO1Converter, Consumer<? super T2> o2Setter) {
	return new BiDirectionalMappingBinding<>(o1, o1ToO2Converter, o2, o2Getter, o2ToO1Converter, o2Setter);
    }
    
    public static <T1, T2> BiDirectionalMappingBinding<T1, T2> createBiDirectionalMappingBinding(
	    Observable o1, Supplier<? extends T1> o1Getter, Function<? super T1, ? extends T2> o1ToO2Converter, Consumer<? super T1> o1Setter,
	    Property<T2> o2, Function<? super T2, ? extends T1> o2ToO1Converter) {
	return new BiDirectionalMappingBinding<>(o1, o1Getter, o1ToO2Converter, o1Setter, o2, o2ToO1Converter);
    }
    
    public static <T1, T2> BiDirectionalMappingBinding<T1, T2> createBiDirectionalMappingBinding(
	    Property<T1> o1, Function<? super T1, ? extends T2> o1ToO2Converter,
	    Property<T2> o2, Function<? super T2, ? extends T1> o2ToO1Converter) {
	return new BiDirectionalMappingBinding<>(o1, o1ToO2Converter, o2, o2ToO1Converter);
    }

    public static <T> UniDirectionalBinding<T> createBinding(
	    Observable o1, Supplier<? extends T> o1Getter, Consumer<? super T> o2Setter) {
	return new UniDirectionalBinding<>(o1, o1Getter, o2Setter);
    }
    
    public static <T> UniDirectionalBinding<T> createBinding(
	    ReadOnlyProperty<T> o1, Consumer<? super T> o2Setter) {
	return new UniDirectionalBinding<>(o1, o2Setter);
    }

    public static <T1, T2> UniDirectionalMappingBinding<T1, T2> createMappingBinding(
	    Observable o1, Supplier<? extends T1> o1Getter, Function<? super T1, ? extends T2> o1ToO2Converter, Consumer<? super T2> o2Setter) {
	return new UniDirectionalMappingBinding<>(o1, o1Getter, o1ToO2Converter, o2Setter);
    }
    
    public static <T1, T2> UniDirectionalMappingBinding<T1, T2> createMappingBinding(
	    ReadOnlyProperty<T1> o1, Function<? super T1, ? extends T2> o1ToO2Converter, Consumer<? super T2> o2Setter) {
	return new UniDirectionalMappingBinding<>(o1, o1ToO2Converter, o2Setter);
    }

}
