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
package libSB.fx.fullscreenStage;

import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Simon Berndt
 */
public enum FullscreenToolkit {
    
    INSTANCE;

    private FullscreenToolkit() {
    }

    public static <N extends Node> FullscreenHandle<N> showFullScreen(N aNode) {
        final FullscreenStage<N> stage = new FullscreenStage<>(aNode);
        final CompletableFuture<N> promise = new CompletableFuture<>();
        stage.addEventHandler(WindowEvent.WINDOW_HIDING, (Event event) -> promise.complete(aNode));
        stage.fullScreenProperty().addListener((Observable o) -> {
            if (!stage.isFullScreen()) {
                promise.complete(aNode);
            }
        });
        stage.show();
        stage.setFullScreen(true);
        CompletableFuture<N> nodeReleased = promise.thenApply((N node) -> {
            stage.releaseResources();
            return node;
        });
        return new FullscreenHandle<>(stage, nodeReleased);
    }

    public static class FullscreenHandle<T extends Node> {

        private final CompletableFuture<T> stageClosed;
        private final FullscreenStage<T> stage;

        private FullscreenHandle(FullscreenStage<T> stage, CompletableFuture<T> stageClosed) {
            this.stageClosed = stageClosed;
            this.stage = stage;
        }

        public CompletableFuture<T> isStageClosed() {
            return stageClosed;
        }
        
        public void reclaimNode() {
            if (Platform.isFxApplicationThread()) {
                stage.hide();
            } else {
                Platform.runLater(stage::hide);
            }
        }
    }

    private static class FullscreenStage<T extends Node> extends Stage {

        private final T contend;
        private final Pane contendWrapper;

        private FullscreenStage(T contend) {
            initStyle(StageStyle.UNDECORATED);
            this.contend = contend;
            this.contendWrapper = new StackPane(contend);
            final Scene scene = new Scene(contendWrapper);
            setFullScreenExitHint("");
            setTitle("Fullscreen");
            setAlwaysOnTop(true);
            setScene(scene);
        }

        private void releaseResources() {
            contendWrapper.getChildren().remove(contend);
        }
    }

}
