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
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.powerbot.script.rt6.ClientAccessor;
import org.powerbot.script.rt6.ClientContext;
import org.qosmiof2.qfighter.QFighter;
import org.qosmiof2.qfighter.data.Food;
import org.qosmiof2.qfighter.data.Npc;
import org.qosmiof2.qfighter.nodes.Attack;
import org.qosmiof2.qfighter.nodes.Eat;
import org.qosmiof2.qfighter.nodes.Walk;

public class Gui extends ClientAccessor {

	public Gui(ClientContext ctx) {
		super(ctx);
		init();
	}

	private Food food;
	private Npc npc;

	private JFrame frame = new JFrame("QFighter");
	private JPanel foodPanel = new JPanel();
	private JPanel attackPanel = new JPanel();
	private JPanel potPanel = new JPanel();
	private JPanel startPanel = new JPanel();
	private JButton button = new JButton("Start");
	private JLabel label = new JLabel("Please select: ");
	private JLabel label1 = new JLabel("Please select: ");
	private JLabel label2 = new JLabel("Please select: ");
	private JCheckBox checkBoxLogOut = new JCheckBox(
			"Log out when run out of food.");
	private JComboBox<Food> foodCb = new JComboBox<Food>(Food.values());
	private JComboBox<Npc> npcCb = new JComboBox<Npc>(Npc.values());
	private JTabbedPane tp = new JTabbedPane();
	private final Font font = new Font("Arial", 1, 12);
	private JLabel warningLabel = new JLabel("Bot carefully!");

	private void init() {
		foodPane();
		attackPane();
		potPane();
		startPane();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(300, 150);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(tp);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ctx.controller.stop();
			}
		});
		tp.add(foodPanel, "Food");
		tp.add(attackPanel, "Attack");
		tp.add(potPanel, "Potions");
		tp.add(startPanel, "Start");
		tp.setFocusable(false);

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

	private void attackPane() {
		attackPanel.setLayout(new GroupLayout(attackPanel));
		attackPanel.add(label1);
		attackPanel.add(npcCb);

		label1.setBounds(5, 5, 100, 20);
		npcCb.setBounds(5, 30, 100, 30);
		npcCb.setFocusable(false);
	}

	private void potPane() {
		potPanel.setLayout(new GroupLayout(potPanel));
		potPanel.add(label2);
		label2.setBounds(5, 5, 100, 20);
	}

	private void startPane() {
		startPanel.setLayout(new GroupLayout(startPanel));
		startPanel.add(button);
		startPanel.add(warningLabel);
		button.setBounds(10, 60, 270, 30);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				food = (Food) foodCb.getSelectedItem();
				npc = (Npc) npcCb.getSelectedItem();
				System.out.println(food + " " + npc);
				QFighter.nodes.add(new Attack(ctx, npc));
				QFighter.nodes.add(new Walk(ctx, npc));
				QFighter.nodes.add(new Eat(ctx, food));
			}

		});
		warningLabel.setForeground(Color.RED);
		warningLabel.setLocation(100, 10);
		warningLabel.setFont(font);
		warningLabel.setSize(100, 20);
	}
}
