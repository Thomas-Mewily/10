// Made by Thomas / Mewily - 22/03/2022
// GitHub : https://github.com/Thomas-Mewily
// Ytb: https://www.youtube.com/channel/UCMzM4J9w0OEAb077mfrfXog
// Licence : Attribution 4.0 International (CC BY 4.0) : https://creativecommons.org/licenses/by/4.0/

public class Main 
{
    public static void main(String[] args) 
    {
        ConsoleColor.Reset();

        var g = Game.load(Game.LastGamePath);
        if(g == null){ g = Game.New(); }
        else
        {
            System.out.println("Reload last game ? (" + g.p1().name() + " versus " + g.p2().name() + "). (y/n)");
            var s = ConsoleInput.readLine();
            if(s.length() >= 1 && Character.toLowerCase(s.charAt(0)) == 'n')
            { 
                g = Game.New();
            }
        }

        while(true)
        {
            g.run();
            System.out.println();
            g = Game.New();
        }
    }
}