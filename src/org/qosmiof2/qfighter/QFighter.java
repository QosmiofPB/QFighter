package org.qosmiof2.qfighter;

import java.awt.EventQueue;
import java.util.ArrayList;

import org.powerbot.script.PollingScript;
import org.powerbot.script.Script.Manifest;
import org.qosmiof2.qfighter.framework.Node;
import org.qosmiof2.qfighter.gui.Gui;

@Manifest(description = "Lets fight 'em!", name = "QFighter")
public class QFighter extends
		PollingScript<org.powerbot.script.rt6.ClientContext> {

	public static ArrayList<Node> nodes = new ArrayList<Node>();

	@Override
	public void start() {
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
				node.execute();
			}
		}
	}

}
