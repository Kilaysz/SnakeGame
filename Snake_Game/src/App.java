import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int board_width = 600;
        int board_height = board_width;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(board_width, board_height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Snake_Game snake_Game = new Snake_Game(board_height, board_width);
        frame.add(snake_Game);
        frame.pack();
        snake_Game.requestFocus();
    }
}
