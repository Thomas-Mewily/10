public class Piece
{
    public enum PieceType 
    {
        P1,
        P2,
        Absent,
        OutOfGrid,
    }

    private final PieceType Type;
    public PieceType type() { return Type;  }

    public Piece(PieceType type) { Type = type; }

    public void print()
    {
        char display = '.';
        switch(type())
        {
            case Absent: break;
            case OutOfGrid: display = '?'; break;
            case P1:        display = 'X'; break;
            case P2:        display = 'O'; break;
            default: Useful.crash("Unknow piece "+type()); break;
        }
        System.out.print(display);
    }

    public static final Piece OutOfGrid = new Piece(PieceType.OutOfGrid);
    public static final Piece Absent    = new Piece(PieceType.Absent);
}