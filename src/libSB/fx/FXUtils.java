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
package libSB.fx;

import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Simon Berndt
 */
public final class FXUtils {

    private FXUtils() {
    }
    
    public static CompletableFuture<Clipboard> getClipboard() {
	return CompletableFuture.supplyAsync(Clipboard::getSystemClipboard, Platform::runLater);
    }
    
    public static AnchorPane wrap(Node aNode) {
	AnchorPane anchorPane = new AnchorPane(aNode);
	insetInto(anchorPane, aNode);
	return anchorPane;
    }
    
    public static AnchorPane wrap(Node aNode, Insets insets) {
	AnchorPane anchorPane = new AnchorPane(aNode);
	insetInto(anchorPane, aNode, insets);
	return anchorPane;
    }
    
    public static void insetInto(AnchorPane anchorPane, Node aNode) {
	AnchorPane.setTopAnchor(aNode, 0.0);
	AnchorPane.setRightAnchor(aNode, 0.0);
	AnchorPane.setBottomAnchor(aNode, 0.0);
	AnchorPane.setLeftAnchor(aNode, 0.0);
    }
    
    public static void insetInto(AnchorPane anchorPane, Node aNode, Insets insets) {
	AnchorPane.setTopAnchor(aNode, insets.getTop());
	AnchorPane.setRightAnchor(aNode, insets.getRight());
	AnchorPane.setBottomAnchor(aNode, insets.getBottom());
	AnchorPane.setLeftAnchor(aNode, insets.getLeft());
    }
    

}
