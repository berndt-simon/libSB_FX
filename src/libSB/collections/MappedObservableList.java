/*
 * The MIT License
 *
 * Copyright 2016 Simon Berndt.
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
package libSB.collections;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.TransformationList;

/**
 *
 * @author Simon Berndt
 */
public class MappedObservableList<I, O> extends TransformationList<O, I> {

    private final Function<? super I, O> mapper;

    public MappedObservableList(ObservableList<I> source, Function<? super I, O> mapper) {
        super(source);
        this.mapper = mapper;
    }

    @Override
    public int getSourceIndex(int index) {
        return index;
    }

    @Override
    public O get(int index) {
        return mapper.apply(getSource().get(index));
    }

    @Override
    public int size() {
        return getSource().size();
    }

    @Override
    protected void sourceChanged(ListChangeListener.Change<? extends I> c) {
        fireChange(new MappedChange(this, c));
    }

    private class MappedChange extends ListChangeListener.Change<O> {

        private final ListChangeListener.Change<? extends I> sourceChange;

        public MappedChange(ObservableList<O> list, ListChangeListener.Change<? extends I> sourceChange) {
            super(list);
            this.sourceChange = sourceChange;
        }

        @Override
        public boolean wasAdded() {
            return sourceChange.wasAdded();
        }

        @Override
        public boolean wasRemoved() {
            return sourceChange.wasRemoved();
        }

        @Override
        public boolean wasReplaced() {
            return sourceChange.wasReplaced();
        }

        @Override
        public boolean wasUpdated() {
            return sourceChange.wasUpdated();
        }

        @Override
        public boolean wasPermutated() {
            return sourceChange.wasPermutated();
        }

        @Override
        public int getPermutation(int i) {
            return sourceChange.getPermutation(i);
        }

        @Override
        protected int[] getPermutation() {
            throw new AssertionError("Unreachable code");
        }

        @Override
        public List<O> getRemoved() {
            return sourceChange.getRemoved().stream().map(mapper).collect(Collectors.toList());
        }

        @Override
        public int getFrom() {
            return sourceChange.getFrom();
        }

        @Override
        public int getTo() {
            return sourceChange.getTo();
        }

        @Override
        public boolean next() {
            return sourceChange.next();
        }

        @Override
        public void reset() {
            sourceChange.reset();
        }
    }

}
