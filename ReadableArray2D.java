public abstract class ReadableArray2D<T>
{
    protected int _width, _height;
    public int width () { return _width;  }
    public int height() { return _height; }

    public boolean isIndexValid(int x, int y){ return x >= 0 && x < width() && y >= 0 && y < height(); }

    public abstract T get(int x, int y);

    public ReadableArray2D(int width, int height)
    {
        _width = width;
        _height = height;
    }
}