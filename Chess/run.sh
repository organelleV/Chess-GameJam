NAME="Main"

if test -f $NAME.class
then
  DATE1=$(date +%s -r ${NAME}.java)
  DATE2=$(date +%s -r ${NAME}.class)

  if [ ${DATE1} -ge ${DATE2} ];
  then
    javac $NAME.java && java $NAME # run javac only when the .java file was modified after the .class file
  else
    java $NAME
  fi

else
  javac $NAME.java && java $NAME
fi
