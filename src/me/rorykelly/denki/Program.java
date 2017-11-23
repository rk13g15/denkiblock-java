package me.rorykelly.denki;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

import me.rorykelly.denki.Denki.JoinedState;

public class Program {

	protected static final int SCALE_FACTOR = 8;

	public static void main(String[] args) {
		new TestWindow();
	}
}

@SuppressWarnings("serial")
class TestWindow extends JFrame {

	private static final Color RED_BASE_COLOR = new Color(248, 24, 24);
	private static final Color RED_HIGHLIGHT_COLOR = new Color(248, 144, 24);
	private static final Color RED_SHADOW_COLOR = new Color(168, 24, 88);
	private static final Color GREEN_BASE_COLOR = new Color(24, 184, 24);
	private static final Color GREEN_HIGHLIGHT_COLOR = new Color(168, 248, 24);
	private static final Color GREEN_SHADOW_COLOR = new Color(24, 120, 64);
	private static final Color EDGE_BASE_COLOR = new Color(248, 224, 224);
	private static final Color EDGE_HIGHLIGHT_COLOR = new Color(248, 248, 248);
	private static final Color EDGE_SHADOW_COLOR = new Color(224, 168, 120);

	private Puzzle puzzle;

	private JPanel puzzlePanel;
	private BufferedImage puzzleImage;
	private JPanel buttonPanel;
	private JButton up;
	private JButton down;
	private JButton left;
	private JButton right;
	private JPanel contentPanel;

	protected TestWindow() {
		Puzzle puzzle = new Puzzle();

		generateTestWalls(puzzle);

		Clump block = new Clump(puzzle, 'W');
		block.addDenki(new Edge(JoinedState.NORTHWEST, EDGE_BASE_COLOR, EDGE_HIGHLIGHT_COLOR, EDGE_SHADOW_COLOR,
				new Point(6, 6), block));
		block.addDenki(new Edge(JoinedState.NORTH, EDGE_BASE_COLOR, EDGE_HIGHLIGHT_COLOR, EDGE_SHADOW_COLOR,
				new Point(7, 6), block));
		block.addDenki(new Edge(JoinedState.NORTH, EDGE_BASE_COLOR, EDGE_HIGHLIGHT_COLOR, EDGE_SHADOW_COLOR,
				new Point(8, 6), block));
		block.addDenki(new Edge(JoinedState.NORTHEAST, EDGE_BASE_COLOR, EDGE_HIGHLIGHT_COLOR, EDGE_SHADOW_COLOR,
				new Point(9, 6), block));
		block.addDenki(new Edge(JoinedState.WEST, EDGE_BASE_COLOR, EDGE_HIGHLIGHT_COLOR, EDGE_SHADOW_COLOR,
				new Point(6, 7), block));
		block.addDenki(new Edge(JoinedState.CENTER, EDGE_BASE_COLOR, EDGE_HIGHLIGHT_COLOR, EDGE_SHADOW_COLOR,
				new Point(7, 7), block));
		block.addDenki(new Edge(JoinedState.CENTER, EDGE_BASE_COLOR, EDGE_HIGHLIGHT_COLOR, EDGE_SHADOW_COLOR,
				new Point(8, 7), block));
		block.addDenki(new Edge(JoinedState.EAST, EDGE_BASE_COLOR, EDGE_HIGHLIGHT_COLOR, EDGE_SHADOW_COLOR,
				new Point(9, 7), block));
		block.addDenki(new Edge(JoinedState.WEST, EDGE_BASE_COLOR, EDGE_HIGHLIGHT_COLOR, EDGE_SHADOW_COLOR,
				new Point(6, 8), block));
		block.addDenki(new Edge(JoinedState.CENTER, EDGE_BASE_COLOR, EDGE_HIGHLIGHT_COLOR, EDGE_SHADOW_COLOR,
				new Point(7, 8), block));
		block.addDenki(new Edge(JoinedState.CENTER, EDGE_BASE_COLOR, EDGE_HIGHLIGHT_COLOR, EDGE_SHADOW_COLOR,
				new Point(8, 8), block));
		block.addDenki(new Edge(JoinedState.EAST, EDGE_BASE_COLOR, EDGE_HIGHLIGHT_COLOR, EDGE_SHADOW_COLOR,
				new Point(9, 8), block));
		block.addDenki(new Edge(JoinedState.SOUTHWEST, EDGE_BASE_COLOR, EDGE_HIGHLIGHT_COLOR, EDGE_SHADOW_COLOR,
				new Point(6, 9), block));
		block.addDenki(new Edge(JoinedState.SOUTH, EDGE_BASE_COLOR, EDGE_HIGHLIGHT_COLOR, EDGE_SHADOW_COLOR,
				new Point(7, 9), block));
		block.addDenki(new Edge(JoinedState.SOUTH, EDGE_BASE_COLOR, EDGE_HIGHLIGHT_COLOR, EDGE_SHADOW_COLOR,
				new Point(8, 9), block));
		block.addDenki(new Edge(JoinedState.SOUTHEAST, EDGE_BASE_COLOR, EDGE_HIGHLIGHT_COLOR, EDGE_SHADOW_COLOR,
				new Point(9, 9), block));

		Clump red_a = new Clump(puzzle, 'R');
		red_a.addDenki(new Gumblock(JoinedState.TOP, true, true, RED_BASE_COLOR, RED_HIGHLIGHT_COLOR, RED_SHADOW_COLOR,
				new Point(3, 3), red_a));
		red_a.addDenki(new Gumblock(JoinedState.BOTTOM, true, true, RED_BASE_COLOR, RED_HIGHLIGHT_COLOR,
				RED_SHADOW_COLOR, new Point(3, 4), red_a));

		Clump red_b = new Clump(puzzle, 'R');
		red_b.addDenki(new Gumblock(JoinedState.SINGLE, true, true, RED_BASE_COLOR, RED_HIGHLIGHT_COLOR,
				RED_SHADOW_COLOR, new Point(2, 2), red_b));

		Clump green_a = new Clump(puzzle, 'G');
		green_a.addDenki(new Gumblock(JoinedState.LEFT, true, true, GREEN_BASE_COLOR, GREEN_HIGHLIGHT_COLOR,
				GREEN_SHADOW_COLOR, new Point(11, 11), green_a));
		green_a.addDenki(new Gumblock(JoinedState.RIGHT, true, true, GREEN_BASE_COLOR, GREEN_HIGHLIGHT_COLOR,
				GREEN_SHADOW_COLOR, new Point(12, 11), green_a));

		Clump green_b = new Clump(puzzle, 'G');
		green_b.addDenki(new Gumblock(JoinedState.SINGLE, true, true, GREEN_BASE_COLOR, GREEN_HIGHLIGHT_COLOR,
				GREEN_SHADOW_COLOR, new Point(13, 5), green_b));

		Clump green_c = new Clump(puzzle, 'G');
		green_c.addDenki(new Gumblock(JoinedState.SINGLE, true, true, GREEN_BASE_COLOR, GREEN_HIGHLIGHT_COLOR,
				GREEN_SHADOW_COLOR, new Point(10, 2), green_c));

		this.puzzle = puzzle;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				init();

			}
		});
	}

	private Clump generateTestWalls(Puzzle puzzle) {
		Clump clump = new Clump(puzzle, 'W');
		for (int r = 1; r < 15; r++) {
			for (int c = 1; c < 15; c++) {
				if ((r == 1 || r == 14) || c == 1 || c == 14) {
					clump.addDenki(new Edge(
							c == 1 ? (r == 1 ? JoinedState.NORTHWEST
									: (r == 14 ? JoinedState.SOUTHWEST : JoinedState.WESTEAST))
									: (c == 14
											? (r == 1 ? JoinedState.NORTHEAST
													: (r == 14 ? JoinedState.SOUTHEAST : JoinedState.WESTEAST))
											: JoinedState.NORTHSOUTH),
							new Color(248, 224, 224), new Color(248, 248, 248), new Color(224, 168, 120),
							new Point(c, r), clump));
				}
			}
		}
		return clump;
	}

	protected TestWindow(Puzzle puzzle) {
		this.puzzle = puzzle;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				init();

			}
		});
	}

	private void init() {
		initInfo();
		initPuzzle();
		initButtons();
		initContent();
		setVisible(true);
		repaint();
	}

	private void initInfo() {
		setTitle("Test...");
		setSize(new Dimension((128 * Program.SCALE_FACTOR) + 34, (128 * Program.SCALE_FACTOR) + 120));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		getInsets().set(0, 0, 0, 0);

	}

	private void initPuzzle() {
		puzzlePanel = new JPanel();
		puzzleImage = new BufferedImage((128 * Program.SCALE_FACTOR), (128 * Program.SCALE_FACTOR),
				BufferedImage.TYPE_INT_RGB);
		puzzlePanel.setPreferredSize(new Dimension((128 * Program.SCALE_FACTOR), (128 * Program.SCALE_FACTOR)));

	}

	private void initButtons() {
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(128, 65));
		up = new JButton("^");
		down = new JButton("v");
		left = new JButton("<");
		right = new JButton(">");

		up.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						puzzle.move(Direction.UP);
						repaint();

					}
				});

			}
		});
		down.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						puzzle.move(Direction.DOWN);
						repaint();

					}
				});

			}
		});
		left.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						puzzle.move(Direction.LEFT);
						repaint();

					}
				});
			}
		});
		right.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						puzzle.move(Direction.RIGHT);
						repaint();

					}
				});

			}
		});

		buttonPanel.add(up);
		buttonPanel.add(down);
		buttonPanel.add(left);
		buttonPanel.add(right);

	}

	private void initContent() {
		contentPanel = new JPanel();

		contentPanel.add(puzzlePanel);
		contentPanel.add(buttonPanel);

		setContentPane(contentPanel);

	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(puzzleImage, 16, 40, null);

		puzzle.draw(puzzleImage.getGraphics());
		g.drawImage(puzzleImage, 16, 40, null);
	}
}
