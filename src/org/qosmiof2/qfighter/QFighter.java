package org.qosmiof2.qfighter;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script.Manifest;
import org.powerbot.script.rt4.Skills;
import org.qosmiof2.qfighter.framework.Node;
import org.qosmiof2.qfighter.gui.Gui;

@Manifest(description = "Lets fight 'em!", name = "QFighter")
public class QFighter extends
		PollingScript<org.powerbot.script.rt6.ClientContext> implements
		PaintListener {

	public static ArrayList<Node> nodes = new ArrayList<Node>();
	private int x, y;
	private int startExp, startLvl;

	@Override
	public void start() {
		if (ctx.game.clientState() == 11) {
			startExp = (ctx.skills.experience(Skills.STRENGTH)
					+ ctx.skills.experience(Skills.ATTACK)
					+ ctx.skills.experience(Skills.MAGIC)
					+ ctx.skills.experience(Skills.DEFENSE) + ctx.skills
					.experience(Skills.RANGE));

			startLvl = (ctx.skills.realLevel(Skills.STRENGTH)
					+ ctx.skills.realLevel(Skills.ATTACK)
					+ ctx.skills.realLevel(Skills.MAGIC)
					+ ctx.skills.realLevel(Skills.DEFENSE) + ctx.skills
					.realLevel(Skills.RANGE));
		}
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Gui(ctx);
			}

		});
	}

	@Override
	public void poll() {
		for (Node node : nodes) {
			if (node.activate()) {
				System.out.println(node.toString());
				node.execute();
			}
		}
	}

	@Override
	public void repaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		x = ctx.mouse.getLocation().x;
		y = ctx.mouse.getLocation().y;

		g.setColor(Color.WHITE);
		g.drawLine(x - 5, y, x + 5, y);
		g.drawLine(x, y - 5, x, y + 5);

		g.setColor(new Color(0, 0, 0, 120));
		g.fillRect(1, 1, 150, 250);
		g.setColor(Color.BLACK);
		g.drawRect(1, 1, 150, 250);

		g.setColor(Color.WHITE);
		g.drawString("Run time: ", 5, 20);
		g.drawString(
				"Exp gained: "
						+ ((ctx.skills.experience(Skills.STRENGTH)
								+ ctx.skills.experience(Skills.ATTACK)
								+ ctx.skills.experience(Skills.MAGIC)
								+ ctx.skills.experience(Skills.DEFENSE) + ctx.skills
									.experience(Skills.RANGE)) - startExp), 5, 35);
		g.drawString(
				"Lvls gained: "
						+ ((ctx.skills.realLevel(Skills.STRENGTH)
								+ ctx.skills.realLevel(Skills.ATTACK)
								+ ctx.skills.realLevel(Skills.MAGIC)
								+ ctx.skills.realLevel(Skills.DEFENSE) + ctx.skills
									.realLevel(Skills.RANGE)) - startLvl), 5, 50);
	}

}
