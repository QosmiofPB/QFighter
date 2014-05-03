package org.qosmiof2.qfighter.nodes;

import org.powerbot.script.rt6.ClientContext;
import org.qosmiof2.qfighter.data.Food;
import org.qosmiof2.qfighter.framework.Node;

public class LogOut extends Node {

	private Food food;

	public LogOut(ClientContext ctx, Food food) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().id(food.getCookedId()).isEmpty();
	}

	@Override
	public void execute() {
		ctx.game.logout(true);

	}

}
