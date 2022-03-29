import java.io.IOException;
import java.nio.CharBuffer;

/**
 * Represents an invalid readable class.
 */
public class InvalidReadable implements Readable {

  @Override
  public int read(CharBuffer cb) throws IOException {
    throw new IOException("");
  }
}
