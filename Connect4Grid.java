import java.util.*;

public class Connect4Grid extends ReadableArray2D<Piece>
{
    //public abstract void set(int x, int y, T data);

    private List<List<Cellule>> Cells;
    private int[] IndexTopColumn; // -1 if outside
    private Cellule OutOfGrid;
    
    public Connect4Grid(int width, int height)
    {
        super(width, height);
        Useful.assertTrue(width() == 0 || height() == 0, "Each dimension of Matrice<T> can't be empty");
        IndexTopColumn = new int[width];
        OutOfGrid = new Cellule(Cellule.ERROR_POS, Cellule.ERROR_POS, Piece.OutOfGrid);
        Reset();
    }

    private void Reset()
    {
        Cells = new ArrayList<>(width());
        for(int x = 0; x < width(); x++)
        {
            List<Cellule> sub = new ArrayList<>(height());

            for(int y = 0; y < height(); y++)
            {
                sub.add(new Cellule(x, y, Piece.Absent));
            }
            Cells.add(x, sub);
        }
    }

    @Override
    public Piece get(int x, int y) {  return getCell(x, y).getPiece(); }
    public Cellule getCell(int x, int y) {  return isIndexValid(x, y) ? Cells.get(x).get(y) : OutOfGrid; }

    public Cellule play(int x, Piece piece) 
    {
        int y = indexTopColumn(x);
        if(y >= height())
        {
            Useful.crash("Can't play piece at column "+ x);
        }
        var c = new Cellule(x, y, piece);
        Cells.get(x).set(y, c);
        IndexTopColumn[x]++;
        return c;
    }

    public int indexTopColumn(int x) { return IndexTopColumn[x]; }
    
    class Cellule
    {
        public static final int ERROR_POS = -1;

        private Piece piece;
        private int x, y;

        public Piece getPiece() { return piece; }

        public boolean isOutside() { return isIndexValid(x,y); }

        public Cellule(int x, int y, Piece piece)
        {
            this.piece = piece;
            this.x = x;
            this.y = y;
        }

        public Cellule relative(int rx, int ry) { return isOutside() ? getCell(x+rx, y+ry) : this; }
        
        public Cellule right() { return relative( 1, 0); }
        public Cellule left()  { return relative(-1, 0); }
        public Cellule up()    { return relative( 0, 1); }
        public Cellule down()  { return relative( 0,-1); }

        public boolean check()
        {
            if(piece == Piece.OutOfGrid) { return false; }
            return nbAlignedPiece(1, 0) == 4
                || nbAlignedPiece(0, 1) == 4
                || nbAlignedPiece(1, 1) == 4
                || nbAlignedPiece(1,-1) == 4
                ;
        }
        private int nbAlignedPiece(int rx, int ry)
        {
            var t = getPiece().type();
            return nbAlignedPieceUniDirection(t, rx, ry)+nbAlignedPieceUniDirection(t, -rx, -ry)-1;
            // -1 because the middle piece is counted twice
        }
        private int nbAlignedPieceUniDirection(Piece.PieceType pieceToCheck, int rx, int ry)
        {
            if(getPiece().type() != pieceToCheck){ return 0; }
            return 1 + relative(rx, ry).nbAlignedPieceUniDirection(pieceToCheck, rx, ry);
        }
    }
}