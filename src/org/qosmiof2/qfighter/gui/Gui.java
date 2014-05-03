package org.qosmiof2.qfighter.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.powerbot.script.rt6.ClientAccessor;
import org.powerbot.script.rt6.ClientContext;
import org.qosmiof2.qfighter.QFighter;
import org.qosmiof2.qfighter.data.Food;
import org.qosmiof2.qfighter.nodes.Attack;
import org.qosmiof2.qfighter.nodes.Eat;
import org.qosmiof2.qfighter.nodes.LogOut;
import org.qosmiof2.qfighter.nodes.Loot;
import org.qosmiof2.qfighter.nodes.Walk;

public class Gui extends ClientAccessor {

	public Gui(ClientContext ctx) {
		super(ctx);
		init();
	}

	private Food food;
	private JFrame frame = new JFrame("QFighter");

	// Panels
	private JPanel foodPanel = new JPanel();
	private JPanel attackPanel = new JPanel();
	private JPanel potPanel = new JPanel();
	private JPanel lootPanel = new JPanel();
	private JPanel startPanel = new JPanel();

	// Components
	private JButton button = new JButton("Start");
	private JLabel label = new JLabel("Please select: ");
	private JLabel label1 = new JLabel(
			"<HTML>Please type the name of the NPC you want to attack.<br>ie. Man</HTML>");
	private JLabel label2 = new JLabel("Coming soon.");
	private JCheckBox checkBoxLogOut = new JCheckBox(
			"Log out when run out of food.");
	private JComboBox<Food> foodCb = new JComboBox<Food>(Food.values());
	private JTextField tfTarget = new JTextField();
	private JTabbedPane tp = new JTabbedPane();
	private JLabel warningLabel = new JLabel("Bot carefully!");
	private JTextField tfLoot = new JTextField();
	private JLabel lootLabel = new JLabel(
			"<HTML>Please type the name of the object you want to loot<br>(if not, leave empty).<br>ie. Bones</HTML>");

	// Font
	private final Font font = new Font("Arial", 1, 12);

	// JMenu+
	private JMenu menu = new JMenu("File");
	private JMenuItem menuItem = new JMenuItem("Exit");
	private JMenuBar menuBar = new JMenuBar();

	private void init() {
		foodPane();
		targetPane();
		potPane();
		lootPane();
		startPane();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(300, 180);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(tp);
		frame.setJMenuBar(menuBar);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ctx.controller.stop();
			}
		});
		tp.add(foodPanel, "Food");
		tp.add(attackPanel, "Targets");
		tp.add(potPanel, "Potions");
		tp.add(lootPanel, "Loot");
		tp.add(startPanel, "Start");
		tp.setFocusable(false);

		menuBar.add(menu);
		menu.add(menuItem);

		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				ctx.controller.stop();
			}

		});

	}

	private void foodPane() {
		foodPanel.setLayout(new GroupLayout(foodPanel));
		foodPanel.add(label);
		foodPanel.add(foodCb);
		foodPanel.add(checkBoxLogOut);

		label.setBounds(5, 5, 100, 20);
		foodCb.setBounds(5, 30, 100, 30);
		foodCb.setFocusable(false);
		checkBoxLogOut.setFont(font);
		checkBoxLogOut.setBounds(50, 70, 300, 20);
		checkBoxLogOut.setFocusable(false);
	}

	private void targetPane() {
		attackPanel.setLayout(new GroupLayout(attackPanel));
		attackPanel.add(label1);
		attackPanel.add(tfTarget);

		label1.setBounds(5, 5, 500, 35);
		tfTarget.setBounds(5, 50, 250, 40);
		tfTarget.setFont(new Font("Arial", 0, 15));
	}

	private void potPane() {
		potPanel.setLayout(new GroupLayout(potPanel));
		potPanel.add(label2);
		label2.setBounds(5, 5, 100, 20);
	}

	private void lootPane() {
		lootPanel.setLayout(new GroupLayout(lootPanel));
		lootPanel.add(lootLabel);
		lootPanel.add(tfLoot);

		lootLabel.setBounds(5, 5, 500, 38);
		tfLoot.setBounds(5, 50, 250, 40);
		tfLoot.setFont(new Font("Arial", 0, 15));
	}

	private void startPane() {
		startPanel.setLayout(new GroupLayout(startPanel));
		startPanel.add(button);
		startPanel.add(warningLabel);
		button.setBounds(10, 60, 270, 30);
		button.setFocusable(false);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!tfTarget.getText().equals("")) {
					food = (Food) foodCb.getSelectedItem();
					Attack.targetName = tfTarget.getText();
					QFighter.nodes.add(new Attack(ctx, food));
					QFighter.nodes.add(new Walk(ctx));
					QFighter.nodes.add(new Eat(ctx, food));
					if (!tfLoot.equals("")) {
						Loot.name = tfLoot.getText();
						QFighter.nodes.add(new Loot(ctx));
					}
					if (checkBoxLogOut.isSelected()) {
						QFighter.nodes.add(new LogOut(ctx, food));
					}
					frame.dispose();
				}
			}

		});
		warningLabel.setForeground(Color.RED);
		warningLabel.setLocation(100, 10);
		warningLabel.setFont(font);
		warningLabel.setSize(100, 20);
	}
}
