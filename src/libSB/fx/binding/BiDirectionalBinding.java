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
import java.util.function.Supplier;
import javafx.beans.Observable;
import javafx.beans.property.Property;

/**
 *
 * @author Simon Berndt
 */
public final class BiDirectionalBinding<T> {

    private final Observable o1;
    private final Observable o2;
    private final TransferListener<T> o1ToO2;
    private final TransferListener<T> o2ToO1;

    BiDirectionalBinding(Observable o1, Supplier<? extends T> o1Getter, Consumer<? super T> o1Setter, Observable o2, Supplier<? extends T> o2Getter, Consumer<? super T> o2Setter) {
	this.o1 = o1;
	this.o2 = o2;
        this.o1ToO2 = new TransferListener<>(o1Getter, o2Setter);
        this.o2ToO1 = new TransferListener<>(o2Getter, o1Setter);
    }
    
    BiDirectionalBinding(Property<T> o1, Observable o2, Supplier<? extends T> o2Getter, Consumer<? super T> o2Setter) {
	this.o1 = o1;
	this.o2 = o2;
        this.o1ToO2 = new TransferListener<>(o1::getValue, o2Setter);
        this.o2ToO1 = new TransferListener<>(o2Getter, o1::setValue);
    }
    
    BiDirectionalBinding(Observable o1, Supplier<? extends T> o1Getter, Consumer<? super T> o1Setter, Property<T> o2) {
	this.o1 = o1;
	this.o2 = o2;
        this.o1ToO2 = new TransferListener<>(o1Getter, o2::setValue);
        this.o2ToO1 = new TransferListener<>(o2::getValue, o1Setter);
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
