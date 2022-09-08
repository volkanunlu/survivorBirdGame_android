package com.volkanunlu.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch;  //objeler , label textview vs koyduğumuz objeler.
	Texture background;  //Texture objenin image aldığı bir pattern kuş resmi,arkaplan resmi gibi.
	Texture bird;   //kuş için tasarladık.
	Texture bee1;  //arı için tasarladık.
	Texture bee2;
	Texture bee3;
	float birdX=0;
	float birdY=0;
	int gameState=0;
	float velocity=0; //hız
	float gravity=0.3f;
	float enemyVelocity=2;
	Random random;
	int score=0;
	int scoredEnemy=0;
	BitmapFont font;
	BitmapFont font2;
	Circle birdCircles;
	ShapeRenderer shapeRenderer;


	int numberOfEnemies=4;
	float [] enemyX= new float[numberOfEnemies];
	float [] offSet=new float[numberOfEnemies];
	float [] offSet2=new float[numberOfEnemies];
	float [] offSet3=new float[numberOfEnemies];

	float distance=0;

	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;


	@Override
	public void create () {   //oyun açıldığında olacak şeyler
		batch = new SpriteBatch();
		background=new Texture("background.png");
		bird=new Texture("bird.png");
		bee1=new Texture("bee.png");
		bee2=new Texture("bee.png");
		bee3=new Texture("bee.png");

		distance=Gdx.graphics.getWidth()/2; //mesafe tanımladık ekranın yarısı kadar verdik ölçü olarak.
		random=new Random();
		font=new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2=new BitmapFont();
		font2.setColor(Color.RED);
		font2.getData().setScale(6);

		birdX=Gdx.graphics.getWidth() / 2- bird.getHeight() / 2;
		birdY=Gdx.graphics.getHeight()/2;

	//	shapeRenderer=new ShapeRenderer();  //shaperenderer kullanarak circle çizdiricez şekillerim dooğru yerde mi anlamak için (circlelar yani)

				birdCircles=new Circle();
		enemyCircles=new Circle[numberOfEnemies];
		enemyCircles2=new Circle[numberOfEnemies];
		enemyCircles3=new Circle[numberOfEnemies];


		for( int i=0; i<numberOfEnemies; i++)
		{
			enemyX[i]=Gdx.graphics.getWidth()-bee1.getWidth()/2+i*distance;

			offSet[i]=(random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);  //y ekseninde işlem yapmak adına tanımladım.
			offSet2[i]=(random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200); //y ekseninde işlem yapmak adına tanımladım.
			offSet3[i]=(random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200); //y ekseninde işlem yapmak adına tanımladım.

			enemyCircles[i]=new Circle(); //initialize ettik.
			enemyCircles2[i]=new Circle(); //initialize ettik.
			enemyCircles3[i]=new Circle(); //initialize ettik.

		}

	}

	@Override
	public void render () {  //oyun devam ettiği sürece devamlı çağırılacak olan metot.

		batch.begin();  //begin ve end arası hangi objelerin olacağını yazacağız.
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());  //arkaplanımızı verdik.

		if(gameState==1){ //oyun başladıysa

			if(enemyX[scoredEnemy]<Gdx.graphics.getWidth() / 3- bird.getHeight() / 3){

				score++;

				if(scoredEnemy<numberOfEnemies-1){

					scoredEnemy++;
				}
				else{
					scoredEnemy=0;
				}


			}



			if (Gdx.input.justTouched()){

				velocity=-10;
			}


			for (int i=0; i<numberOfEnemies; i++)
			{

				if(enemyX[i]<Gdx.graphics.getWidth()/15)  //arının yeri, getwidth alanının yerinden küçüldüyse yani arının boyutunun altına indiyse
				{
					enemyX[i]=enemyX[i]+numberOfEnemies*distance; //başa almaya çalışıcaz.

					offSet[i]=(random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);  //y ekseninde işlem yapmak adına tanımladım.
					offSet2[i]=(random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200); //y ekseninde işlem yapmak adına tanımladım.
					offSet3[i]=(random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200); //y ekseninde işlem yapmak adına tanımladım.


				}
				else{
					enemyX[i]=enemyX[i]-enemyVelocity;

				}


				enemyX[i]= enemyX[i]-enemyVelocity;  //her enemy için hızı verdik.

				batch.draw(bee1,enemyX[i],Gdx.graphics.getHeight()/2+offSet[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10); //arılarımı çizdiriyorum
				batch.draw(bee2,enemyX[i],Gdx.graphics.getHeight()/2+offSet2[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10); //arılarımı çizdiriyorum
				batch.draw(bee3,enemyX[i],Gdx.graphics.getHeight()/2+offSet3[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10); //arılarımı çizdiriyorum

				enemyCircles[i]=new Circle(enemyX[i]+ Gdx.graphics.getWidth()/30, + Gdx.graphics.getHeight()/2+offSet[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30 );
				enemyCircles2[i]=new Circle(enemyX[i]+ Gdx.graphics.getWidth()/30, + Gdx.graphics.getHeight()/2+offSet2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30 );
				enemyCircles3[i]=new Circle(enemyX[i]+ Gdx.graphics.getWidth()/30, + Gdx.graphics.getHeight()/2+offSet3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30 );


			}

			if(birdY>0 ){

				velocity=velocity+gravity;  //düşme hızı kuşun
				birdY=birdY-velocity;

			}
			else{
					gameState=2;
			}


			}else if(gameState==0){ //başlamadıysa

			if (Gdx.input.justTouched()){
				gameState=1;
			}

			}else if(gameState==2){ //oyun bittiyse tekrar tıklama ile oyunu başlatabiliriz.

			font2.draw(batch,"Game Over! Tap To Play Again!",300,Gdx.graphics.getHeight()/2);

			if (Gdx.input.justTouched()){
				gameState=1;

				birdY=Gdx.graphics.getHeight()/3;

				for( int i=0; i<numberOfEnemies; i++)
				{
					enemyX[i]=Gdx.graphics.getWidth()-bee1.getWidth()/2+i*distance;

					offSet[i]=(random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200);  //y ekseninde işlem yapmak adına tanımladım.
					offSet2[i]=(random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200); //y ekseninde işlem yapmak adına tanımladım.
					offSet3[i]=(random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-200); //y ekseninde işlem yapmak adına tanımladım.

					enemyCircles[i]=new Circle(); //initialize ettik.
					enemyCircles2[i]=new Circle(); //initialize ettik.
					enemyCircles3[i]=new Circle(); //initialize ettik.

				}

			}
			velocity=0;
			scoredEnemy=0;
			score=0;





		}


		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10); //kuşumuzu verdik.
							//konumlandırma 								//boyut ayarlaması
		font.draw(batch,String.valueOf(score),100,200);
		batch.end();

		birdCircles.set(birdX+Gdx.graphics.getWidth()/30,birdY+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);

	//	shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
	//	shapeRenderer.setColor(Color.BLACK);
	//	shapeRenderer.circle(birdCircles.x, birdCircles.y,birdCircles.radius );

		for(int i=0; i<numberOfEnemies; i++){

		//	shapeRenderer.circle(enemyX[i]+ Gdx.graphics.getWidth()/30, + Gdx.graphics.getHeight()/2+offSet[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30 );
		//	shapeRenderer.circle(enemyX[i]+ Gdx.graphics.getWidth()/30, + Gdx.graphics.getHeight()/2+offSet2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30 );
		//	shapeRenderer.circle(enemyX[i]+ Gdx.graphics.getWidth()/30, + Gdx.graphics.getHeight()/2+offSet3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30 );


			//çarpışmayı yakalamak adına intersector kullanıyoruz.
			if(Intersector.overlaps(birdCircles,enemyCircles[i])|| Intersector.overlaps(birdCircles,enemyCircles2[i])|| Intersector.overlaps(birdCircles,enemyCircles3[i])){

				gameState=2;

			}

		}
	//	shapeRenderer.end();



	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
