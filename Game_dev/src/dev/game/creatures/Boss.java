package dev.game.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.game.Handler;
import dev.game.entity.Entity;
import dev.launcher.Assets;

public class Boss extends Creature{
	private BossHandLeft leftHand;
	private BossHandRight rightHand;
	private int delay=0;
	private float leftYDelta;
	private float rightXDelta;
	private float rightYDelta;
	private float leftXDelta;
	private int leftCooldown=30,leftAttackCount=0,leftAttackLoop=600,leftAttackLoopCount=0;
	private int meleeAttackCount=0,meleeAttackDelay=240;
	private boolean up;
	private boolean right;
	private int rightAttackCount=0;
	private int rightCooldown=120;
	public Boss(Handler handler, float x, float y, int width, int height,BossHandLeft leftHand,BossHandRight rightHand) {
		super(handler, x, y, width, height);
		// TODO Auto-generated constructor stub
		this.maxHealth=20;
		this.health=20;
		this.id=7;
		this.leftHand=leftHand;
		this.rightHand=rightHand;
		this.damage=1;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		idleMove();
		moveHands();
		checkAttacks();
	}

	private void checkAttacks() {
		// TODO Auto-generated method stub
		float yDelta = y-handler.getWorld().getEntityManager().getPlayer().getY();
		if(!(yDelta>-(this.getHeight()+20))) {
			meleeAttackCount=0;
			if(leftAttackLoopCount>=leftAttackLoop) {
				leftAttackLoopCount=0;
			}
			if (leftAttackLoopCount<=150) {
				if(leftAttackCount>leftCooldown) {
					leftAttackCount=0;
					leftHand.spreadAttack();
				}else {
					leftAttackCount++;
				}
				leftAttackLoopCount++;
			}else {
				leftAttackLoopCount++;
			}
			if(rightAttackCount>rightCooldown) {
				rightAttackCount=0;
				rightHand.targetAttack();
			}else {
				rightAttackCount++;
			}
		}else {
			if(meleeAttackCount>=meleeAttackDelay) {
				meleeAttackCount=0;
				Rectangle cb = this.getCollisionBounds(0, 0);
				Rectangle ar = null;
				ar= new Rectangle();
				int arSize=50;
				ar.height=arSize;
				ar.x=cb.x;
				ar.y=cb.y+cb.height;
				ar.width=cb.width;
				for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
					if(e.equals(this)) {continue;}
					if(e.getCollisionBounds(0, 0).intersects(ar)) {
						int deltaX=(int) ((this.getCollisionBounds(0, 0).x+this.getCollisionBounds(0, 0).width/2) - (e.getCollisionBounds(0, 0).x+e.getCollisionBounds(0, 0).width/2));
						int deltaY=(int) ((this.getCollisionBounds(0, 0).y+this.getCollisionBounds(0, 0).height/2) - (e.getCollisionBounds(0, 0).y+e.getCollisionBounds(0, 0).height/2));
						e.hurt(damage,deltaX,deltaY);
					}
				}
			}else {
				meleeAttackCount++;
			}
		}
	}

	private void idleMove() {
		// TODO Auto-generated method stub
		if (leftYDelta>10||leftYDelta<-10) {
			up=!up;
		}
		if(up) {
			leftYDelta+=0.25;
		}else {
			leftYDelta-=0.25;
		}
		if (leftXDelta>15||leftXDelta<-15) {
			right=!right;
		}
		if(right) {
			leftXDelta+=0.25;
		}else {
			leftXDelta-=0.25;
		}
		rightXDelta=leftXDelta;
		rightYDelta=leftYDelta;
	}

	private void moveHands() {
		// TODO Auto-generated method stub
		leftHand.setX(this.getX()+this.getWidth()/2-leftHand.getWidth()/2-100+leftXDelta);
		leftHand.setY(this.getY()+this.getHeight()/2-leftHand.getHeight()/2+100+leftYDelta);
		rightHand.setX(this.getX()+this.getWidth()/2-rightHand.getWidth()/2+100+rightXDelta);
		rightHand.setY(this.getY()+this.getHeight()/2-rightHand.getHeight()/2+100+rightYDelta);
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(Assets.magic,(int)(x-handler.getGameCamera().getxOffset()),(int)(y-handler.getGameCamera().getyOffset()-(meleeAttackCount*50)/meleeAttackDelay),width,height,null);

	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

}
