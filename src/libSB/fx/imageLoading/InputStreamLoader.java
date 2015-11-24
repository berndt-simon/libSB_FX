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
package libSB.fx.imageLoading;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author Simon Berndt
 */
public abstract class InputStreamLoader implements PrefetchingImageLoader {
    
    private static final Logger LOG = Logger.getLogger(InputStreamLoader.class.getName());
    
    protected final Executor executor;

    protected InputStreamLoader(Executor executor) {
	this.executor = executor;
    }
    
    @Override
    public CompletableFuture<Image> loadAsync(Path path) {
	return CompletableFuture.supplyAsync(() -> load(path), this.executor);
    }

    public static class BoundedResolution extends InputStreamLoader {

	private final double maxWidth;
	private final double maxHeight;
	private final boolean smooth;
	private final boolean preserveRatio;

	public BoundedResolution(Executor executor, double maxWidth, double maxHeight, boolean smooth, boolean preserveRatio) {
	    super(executor);
	    this.maxHeight = maxHeight;
	    this.maxWidth = maxWidth;
	    this.smooth = smooth;
	    this.preserveRatio = preserveRatio;
	}

	@Override
	public Image load(Path path) {
	    try (final InputStream in = Files.newInputStream(path, StandardOpenOption.READ)) {
		return new Image(in, this.maxWidth, this.maxHeight, preserveRatio, smooth);
	    } catch (final IOException ex) {
		LOG.log(Level.INFO, null, ex);
	    }
	    return null;
	}

    }

    public static class FullResolution extends InputStreamLoader {

	public FullResolution(Executor executor) {
	    super(executor);
	}

	@Override
	public Image load(Path path) {
	    try (final InputStream in = Files.newInputStream(path, StandardOpenOption.READ)) {
		return new Image(in);
	    } catch (final IOException ex) {
		LOG.log(Level.INFO, null, ex);
	    }
	    return null;
	}

    }
}
