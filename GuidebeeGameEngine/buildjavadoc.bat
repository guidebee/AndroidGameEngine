del javadoc\*.html /S
javadoc -locale en_US   -subpackages com.guidebee -exclude com.guidebee.game.engine:com.guidebee.game.engine.utils  -public -d javadoc -sourcepath src -doclet com.google.doclava.Doclava -docletpath "doclava-1.0.6.jar" -title "Guidebee Game Engine API"  -hdf project.name "Guidebee Game Engine" -hide 105 -classpath "c:\Android\sdk\platforms\android-19\android.jar;c:\Android\sdk\platforms\android-19\data\res;c:\Android\sdk\tools\support\annotations.jar;C:\Scala\lib\scala-library.jar" -XDignore.symbol.file