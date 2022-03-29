import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Game
{
    private Connect4Grid grid;
    private static final String ColumnName = "abcdefg";
    public  static final String LastGamePath = "last.txt";
    
    private int turnNb;
    private Player p1, p2;
    private StateEnum state; 
    private String saveCode; // list of column name

    private Player currentPlayer() { return turnNb % 2 == 0 ? p1 : p2;};
    public Player p1() { return p1; }
    public Player p2() { return p2; }

    public StateEnum getState() { return state; }
    public enum StateEnum { Running, P1Win, P2Win, ExAequo } // questionable

    private Game(Player p1, Player p2)
    {
        this.grid = new Connect4Grid(7, 6);
        this.turnNb = 0;
        this.p1 = p1;
        this.p2 = p2;
        this.state = StateEnum.Running;
        this.saveCode = "";
    }

    public static Game New() { return new Game(Player.ask(1), Player.ask(2)); }

    public void run()
    {
        if(state == StateEnum.Running)
        { 
            do
            {
                runTurn();
                save(LastGamePath);
            }while(state == StateEnum.Running);
        }

        writeln();
        displayState();
    }

    public boolean save(String path)
    {
        FileWriter writer = null;
        try
        {
            writer = new FileWriter(path);
            p1.save(writer);
            p2.save(writer);
            writer.write(saveCode);
        }catch (Exception e)
        {
            return false;
        }finally
        {
            if(writer != null)
            {
                try{ writer.close(); }catch (Exception e2){}
            }
        }
        return true;
    }

    public static Game load(String path)
    {
        String content = "";
        try
        {
            content = new String(Files.readString(Paths.get(path)));
            String[] val = content.split("\n");
            Game g = new Game(Player.load(val[0], Piece.PieceType.P1), Player.load(val[1], Piece.PieceType.P2));
            for(char c : val[2].toCharArray())
            {
                if(g.state == StateEnum.Running)
                {
                    g.playPiece(ColumnName.indexOf(c));
                }
            }
            return g;
        } 
        catch (Exception e) 
        {
            return null;
        }
    }

    public void displayState()
    {
        switch(state)
        {
            case ExAequo: System.out.println("No winner."); break;
            case P1Win:   System.out.println("And the winner is " + p1.name() + " ! ("+ p2.name() +" lost.)"); break;
            case P2Win:   System.out.println("And the winner is " + p2.name() + " ! ("+ p1.name() +" lost.)"); break;
            case Running: System.out.println("The game is running."); break;
            default: Useful.crash("Unknow game state : " + state);    break;
        }
    }



    private void runTurn()
    {
        if(turnNb >= grid.width()*grid.height())
        {
            state = StateEnum.ExAequo;
            return;
        }
        writeln();
        System.out.print("It's your turn "+ currentPlayer().name()+". (");
        currentPlayer().printPiece();
        System.out.println(")");

        playPiece(getValidColumn());
    }

    private void playPiece(int x)
    {
        saveCode += ColumnName.charAt(x);
        if(grid.play(x, currentPlayer().piece()).check())
        {
            state = turnNb % 2 == 0 ? StateEnum.P1Win : StateEnum.P2Win;
            return;
        }
        turnNb++;
        state = StateEnum.Running;
    }

    private int getValidColumn()
    {
        while (true)
        {
            int x = currentPlayer().columnWhereToPlay(ColumnName);
            if(grid.indexTopColumn(x) < grid.height())
            {
                return x;
            }
            System.out.println("Invalid column");
        }
    }

    private void writeln()
    {
        System.out.println();
        System.out.println("Turn n°" + (turnNb+1));
        final String separator = "  ";

        for(var c : ColumnName.toCharArray())
        {
            System.out.print(c+separator);
        }
        System.out.println();

        for(int y = grid.height()-1; y >= 0; y--)
        {
            for(int x = 0; x < grid.width(); x++)
            {
                var piece = grid.get(x, y);
                if(piece.type() == Piece.PieceType.P1) { ConsoleColor.Set(p1.color()); }
                if(piece.type() == Piece.PieceType.P2) { ConsoleColor.Set(p2.color()); }

                piece.print();
                ConsoleColor.Reset();
                System.out.print(separator);
            }
            System.out.println();
        }
    }
}