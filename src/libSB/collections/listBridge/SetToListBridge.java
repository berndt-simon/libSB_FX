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
package libSB.collections.listBridge;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

/**
 *
 * @author Simon Berndt
 */
public class SetToListBridge<T> implements ListBridge<T> {

    private final ObservableList<T> readOnlyList;

    public SetToListBridge(ObservableSet<T> backgroundSet) {
	final ObservableList<T> reflectedList = FXCollections.observableArrayList(backgroundSet);

	backgroundSet.addListener((SetChangeListener.Change<? extends T> change) -> {
	    if (change.wasRemoved()) {
		reflectedList.remove(change.getElementRemoved());
	    }
	    if (change.wasAdded()) {
		reflectedList.add(change.getElementAdded());
	    }
	});
	    this.readOnlyList = FXCollections.unmodifiableObservableList(reflectedList);
    }

    @Override
    public ObservableList<T> asList() {
	return this.readOnlyList;
    }

}
