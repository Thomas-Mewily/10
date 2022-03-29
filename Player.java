import java.io.FileWriter;

public class Player 
{
    private final String name;
    private final Piece  piece;
    private final String color;

    public String name()  { return color +name+ ConsoleColor.ANSI_RESET_BLACK_N_WHITE; }
    public Piece  piece() { return piece; }
    public String color() { return color; }

    public Player(String name, Piece piece, String color)
    {
        this.name = name.split(" ")[0];
        this.piece = piece;
        this.color = color;
    }

    public int columnWhereToPlay(String ColumnName)
    {
        while(true)
        {
            System.out.println("Choose a column (letter)");
            var i = ConsoleInput.readLine();
            if(i.length() == 1)
            {
                String s = Character.toLowerCase(i.charAt(0))+"";
                if(ColumnName.contains(s)){ return ColumnName.indexOf(s); }
            }
            System.out.println("Invalid column letter");
        }
    }

    public void printPiece()
    {
        ConsoleColor.Set(color);
        piece.print();
        ConsoleColor.Reset();
    }


    public static Player ask(int number)
    {
        System.out.print("Player "+number+", what's your name ? ");
        String name = ConsoleInput.readLine();
        return new Player(name, new Piece(number == 1 ? Piece.PieceType.P1 : Piece.PieceType.P2), AskColor());
    }

    public static String AskColor()
    {
        while(true)
        {
            ShowColor("r", ConsoleColor.ANSI_RED);
            ShowColor("g", ConsoleColor.ANSI_GREEN);
            ShowColor("b", ConsoleColor.ANSI_BLUE);
            ShowColor("c", ConsoleColor.ANSI_CYAN);
            ShowColor("y", ConsoleColor.ANSI_YELLOW);
            ShowColor("p", ConsoleColor.ANSI_PURPLE);
            ShowColor("d", ConsoleColor.ANSI_BLACK);
            ShowColor("w", ConsoleColor.ANSI_WHITE);
            System.out.print("Choose your color :");

            var r = ConsoleInput.readLine();
            if(r.length() >= 1)
            {
                switch(Character.toLowerCase(r.charAt(0)))
                {
                    case 'r': return ConsoleColor.ANSI_RED;
                    case 'g': return ConsoleColor.ANSI_GREEN;
                    case 'b': return ConsoleColor.ANSI_BLUE;
                    case 'c': return ConsoleColor.ANSI_CYAN;
                    case 'y': return ConsoleColor.ANSI_YELLOW;
                    case 'p': return ConsoleColor.ANSI_PURPLE;
                    case 'd': return ConsoleColor.ANSI_BLACK;
                    case 'w': return ConsoleColor.ANSI_WHITE;
                }
            }
            System.out.println("Invalid color. (use the first letter of the color)");
        }
    }

    private static void ShowColor(String colorName, String colorAnsi)
    {
        ConsoleColor.Set(colorAnsi);
        System.out.print(colorName+", ");
        ConsoleColor.Reset();
    }

    public void save(FileWriter writer)
    {
        try
        {
            writer.write(name+","+ color+"\n");
        }catch (Exception e){}
    }

    public static Player load(String s, Piece.PieceType p)
    {
        var val = s.split(",");
        return new Player(val[0], new Piece(p), val[1]);
    }
}