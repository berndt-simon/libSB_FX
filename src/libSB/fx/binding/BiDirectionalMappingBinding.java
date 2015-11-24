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

/**
 *
 * @author Simon Berndt
 */
public final class BiDirectionalMappingBinding<T1, T2> {

    private final Observable o1;
    private final Observable o2;
    private final MappingTransferListener<T1, T2> o1ToO2;
    private final MappingTransferListener<T2, T1> o2ToO1;

    BiDirectionalMappingBinding(
	    Observable o1, Supplier<? extends T1> o1Getter, Function<? super T1, ? extends T2> o1ToO2Converter, Consumer<? super T1> o1Setter,
	    Observable o2, Supplier<? extends T2> o2Getter, Function<? super T2, ? extends T1> o2ToO1Converter, Consumer<? super T2> o2Setter) {
	this.o1 = o1;
	this.o2 = o2;
        this.o1ToO2 = new MappingTransferListener<>(o1Getter, o1ToO2Converter, o2Setter);
        this.o2ToO1 = new MappingTransferListener<>(o2Getter, o2ToO1Converter, o1Setter);
    }
    
    BiDirectionalMappingBinding(
	    Property<T1> o1, Function<? super T1, ? extends T2> o1ToO2Converter,
	    Observable o2, Supplier<? extends T2> o2Getter, Function<? super T2, ? extends T1> o2ToO1Converter, Consumer<? super T2> o2Setter) {
	this.o1 = o1;
	this.o2 = o2;
        this.o1ToO2 = new MappingTransferListener<>(o1::getValue, o1ToO2Converter, o2Setter);
        this.o2ToO1 = new MappingTransferListener<>(o2Getter, o2ToO1Converter, o1::setValue);
    }
    
    BiDirectionalMappingBinding(
	    Observable o1, Supplier<? extends T1> o1Getter, Function<? super T1, ? extends T2> o1ToO2Converter, Consumer<? super T1> o1Setter,
	    Property<T2> o2, Function<? super T2, ? extends T1> o2ToO1Converter) {
	this.o1 = o1;
	this.o2 = o2;
        this.o1ToO2 = new MappingTransferListener<>(o1Getter, o1ToO2Converter, o2::setValue);
        this.o2ToO1 = new MappingTransferListener<>(o2::getValue, o2ToO1Converter, o1Setter);
    }
    
    BiDirectionalMappingBinding(
	    Property<T1> o1, Function<? super T1, ? extends T2> o1ToO2Converter,
	    Property<T2> o2, Function<? super T2, ? extends T1> o2ToO1Converter) {
	this.o1 = o1;
	this.o2 = o2;
        this.o1ToO2 = new MappingTransferListener<>(o1::getValue, o1ToO2Converter, o2::setValue);
        this.o2ToO1 = new MappingTransferListener<>(o2::getValue, o2ToO1Converter, o1::setValue);
    }

    public void bind() {
        this.o1.addListener(this.o1ToO2);
        this.o2.addListener(this.o2ToO1);
    }

    public void unBind() {
        this.o1.removeListener(this.o1ToO2);
        this.o2.removeListener(this.o2ToO1);
    }

    public void initialTransferO1ToO2() {
        this.o1ToO2.transfer();
    }

    public void initialTransferO2ToO1() {
        this.o2ToO1.transfer();
    }

}
