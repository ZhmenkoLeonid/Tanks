package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;


public class MyGdxGame extends ApplicationAdapter {

	OrthographicCamera camera;
	SpriteBatch batch;

	Texture arrowLeft;
	Texture arrowRight;
	Texture arrowUp;
	Texture arrowDown;
	Texture tankUp;
	Texture tankDown;
	Texture tankLeft;
	Texture tankRight;

	Tank tank= new Tank(800/2 - 64/2, 480/2 - 64/2, 128, 128);

	Vector3 touchPos = new Vector3();

	char direction = 'U';   //

	@Override
	public void create () {
		batch = new SpriteBatch();

		arrowLeft = new Texture(Gdx.files.internal("arrowleft.png"));
		arrowRight = new Texture(Gdx.files.internal("arrowright.png"));
		arrowUp = new Texture(Gdx.files.internal("arrowup.png"));
		arrowDown = new Texture(Gdx.files.internal("arrowdown.png"));
		tankUp = new Texture(Gdx.files.internal("tankup.jpg"));
		tankDown = new Texture(Gdx.files.internal("tankdown.png"));
		tankLeft = new Texture(Gdx.files.internal("tankleft.png"));
		tankRight = new Texture(Gdx.files.internal("tankright.png"));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		TankDraw();
		batch.draw(arrowLeft, 0, 0);
		batch.draw(arrowRight, 128, 2);
		batch.draw(arrowDown, 800 - 128, 0);
		batch.draw(arrowUp, 800 - 128, 128);
		batch.end();

		if (Gdx.input.isTouched(0)) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);

			if (touchPos.x < 128 && touchPos.y < 128) {
				tank.setX(tank.getX() - 2);
				direction = 'L';
			}
			else if (touchPos.x < 256 && touchPos.x > 128 && touchPos.y < 128){
				tank.setX(tank.getX() + 2);
				direction = 'R';
			}
			else if ( (touchPos.x > 800-128) && (touchPos.y > 128) && (touchPos.y < 256) ){
				tank.setY(tank.getY() + 2);
				direction = 'U';
			}
			else if ( (touchPos.x > 800-128) && (touchPos.y < 128) ) {
				tank.setY(tank.getY() - 2 );
				direction = 'D';
			}
		}
	}

	public void TankDraw(){
		switch (direction) {
			case 'U':
				batch.draw(tankUp, tank.getX(), tank.getY());
				break;
			case 'D':
				batch.draw(tankDown, tank.getX(), tank.getY());
				break;
			case 'L':
				batch.draw(tankLeft, tank.getX(), tank.getY());
				break;
			case 'R':
				batch.draw(tankRight, tank.getX(), tank.getY());
				break;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		arrowDown.dispose();
		arrowLeft.dispose();
		arrowRight.dispose();
		arrowUp.dispose();
		tankDown.dispose();
		tankLeft.dispose();
		tankRight.dispose();
		tankUp.dispose();
	}
}
