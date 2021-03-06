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

import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.SortedList;

/**
 *
 * @author Simon Berndt
 */
public class MapToSortedListBridge<E, T> implements ListBridge<T> {

    private final SortedList<T> readOnlyList;

    public MapToSortedListBridge(ObservableMap<E, T> backgroundMap, Comparator<T> comparator) {
        final ObservableList<T> reflectedList = FXCollections.observableArrayList(backgroundMap.values());

        backgroundMap.addListener((MapChangeListener.Change<? extends E, ? extends T> change) -> {
            if (change.wasAdded()) {
                reflectedList.add(change.getValueAdded());
            }
            if (change.wasRemoved()) {
                reflectedList.remove(change.getValueRemoved());
            }
        });

        this.readOnlyList = new SortedList<>(reflectedList, comparator);
    }

    @Override
    public SortedList<T> asList() {
        return this.readOnlyList;
    }

}
