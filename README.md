# SpaceInvaders

![Bild1](https://github.com/m-arnold/SpaceInvaders/blob/master/ReadmeRessources/MainMenu.png)
![Bild2](https://github.com/m-arnold/SpaceInvaders/blob/master/ReadmeRessources/Asteroids.png)

![Bild3](https://github.com/m-arnold/SpaceInvaders/blob/master/ReadmeRessources/Bossfight.png)

A SpaceInvaders game implemented in Java using LibGDX. The project's purpose was fun and to get familiar with libGDX. For further information see [Implementation](#implementation).

### Table of Contents
1. [Get Started](#get-started)
2. [Credits](#credits)
3. [Implementation](#implementation)


### Get Started

1. Clone the repository.
```
git clone https://github.com/m-arnold/SpaceInvaders.git
```

2. Navigate to the repository root folder.

3. Build the project using gradle.
```
./gradlew desktop:dist
```

4. Navigate to the java artifacte.
```
cd ./desktop/build/libs/
```

5. Execute the jar using your java runtime environment.
```
java -jar desktop-1.0.jar 
```

### Credits

#### Textures
All game and menu textures are from kenney.nl:

https://www.kenney.nl/assets/space-shooter-extension

https://www.kenney.nl/assets/space-shooter-redux

https://www.kenney.nl/assets/ui-pack-space-expansion

kenney.nl has lots of free game textures. Please give them a visit.


#### Animations
Explosion: https://opengameart.org/content/2d-explosion-animations-frame-by-frame. Really great explosion animation, visit their facebook: https://www.facebook.com/sinestesiastudio

#### Sounds
Main menu music: https://opengameart.org/content/retro

Game music: https://opengameart.org/content/8-bit-battle-theme-famitracker

Game sounds in general: https://www.kenney.nl/assets/digital-audio 

Explosion sound: https://opengameart.org/content/bombexplosion8bit

### Implementation

This was my first libGDX project and it was learning by doing most of the time. Don't expect libGDX best practises. I tried to keep the code as clean as possible. Some aspects (e.g the entity system class hierarchy) could definitly be improved.
