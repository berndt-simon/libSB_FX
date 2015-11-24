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
package libSB.fx.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Simon Berndt
 */
public class ImageContainerPane extends BorderPane {

    private final ImageView imageView;

    public ImageContainerPane() {
	    this.imageView = new ImageView();
	setCenter(this.imageView);
	BorderPane.setMargin(this.imageView, new Insets(0.0));
	BorderPane.setAlignment(this.imageView, Pos.CENTER);

	    this.imageView.fitWidthProperty().bind(widthProperty());
	    this.imageView.fitHeightProperty().bind(heightProperty());

	    this.imageView.setPreserveRatio(true);
	    this.imageView.setSmooth(true);
    }

    public ImageView getWrappedImageView() {
	return this.imageView;
    }

    @Override
    protected double computeMinHeight(double width) {
	return 0;
    }

    @Override
    protected double computeMinWidth(double height) {
	return 0;
    }

}
