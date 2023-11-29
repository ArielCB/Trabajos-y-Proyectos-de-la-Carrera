#!/bin/bash

funcion0(){
	echo "Buscando IPs en var/log/auth.log" | tee -a /var/log/ipLog.log
	IPS=`tail -100 /var/log/auth.log | tr " " "\n" | grep -E "[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+"`
	MAYOR=0
	for IP in $IPS
	do
		CURRENT=`tail -100 /var/log/auth.log | grep -c "$IP"`
		if test $CURRENT -gt $MAYOR
		then
			MAYOR=$CURRENT
			MAYORIP=$IP
		fi
	done
	if test $MAYOR -eq 0
	then
		echo "No hay IPs en las ultimas lineas de auth.log" | tee -a /var/log/ipLog.log
	else
		echo "$MAYOR $MAYORIP" | tee -a /var/log/ipLog.log
	fi
	echo "Busqueda terminada" | tee -a /var/log/ipLog.log
}

funcionIP(){
	echo "Buscando la IP $1 en el fichero /var/log/auth.log" | tee -a /var/log/ipLog.log
	NUMERO=`grep -c "$1" /var/log/auth.log`
	if test $? -ne 0
        then
                echo "Ha habido un problema en la busqueda" | tee -a /var/log/ipLog.log
                exit 4
        fi
	if test $NUMERO -eq 0
	then
		echo "No se ha encontrado la IP $1 en auth.log" | tee -a /var/log/ipLog.log
	else
		echo "$NUMERO $1" | tee -a /var/log/ipLog.log
	fi
	echo "Busqueda terminada" | tee -a /var/log/ipLog.log
}

funcionPath(){
	echo "Buscando ultima IP del archivo auth.log" | tee -a /var/log/ipLog.log
	IP=`grep -E "[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+" /var/log/auth.log | tail -1 | tr " " "\n" | grep -E "[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+"`
	if test $? -ne 0
        then
        	echo "Ha habido un problema en la busqueda" | tee -a /var/log/ipLog.log
        	exit 4
        fi
        if test -z $IP
        then
                echo "No se ha encontrado ninguna IP en el archivo auth.log" | tee -a /var/log/ipLog.log
		exit 5
        fi
	echo "Buscando la IP: $IP en el directorio $1" | tee -a /var/log/ipLog.log
        COMPROBACION=0
	for FILE in `find $1 -name "*.log"`
	do
		for LINE in `grep "$IP" $FILE`
		do
			echo "$FILE: $IP" | tee -a /var/log/ipLog.log
			COMPROBACION=1
		done
	done
	if test $? -ne 0
        then
        	echo "Ha habido un problema en la busqueda" | tee -a /var/log/ipLog.log
        	exit 4
        fi
        if test $COMPROBACION -eq 0
        then
                echo "No se ha encontrado la IP $IP en ningún archivo .log del directorio." | tee -a /var/log/ipLog.log
        fi
	echo "Busqueda terminada." | tee -a /var/log/ipLog.log
}

funcion2(){
	echo "$2 es una IP y $1 un directorio. Buscamos." | tee -a /var/log/ipLog.log
	COMPROBACION=0
	for FILE in `find $1 -name "*.log"`
	do
		for LINE in `grep "$2" $FILE`
		do
			echo "$FILE: $LINE" | tee -a /var/log/ipLog.log
			COMPROBACION=1
		done
	done
	if test $? -ne 0
	then
		echo "Ha habido un problema en la busqueda" | tee -a /var/log/ipLog.log
		exit 4
	fi
	if test $COMPROBACION -eq 0
	then
		echo "No se ha encontrado la IP en ningún archivo .log del directorio" | tee -a /var/log/ipLog.log
	fi
	echo "Busqueda terminada." | tee -a /var/log/ipLog.log
}

if test $# -gt 2
then
        echo "Error. Uso: \$path \$IP"
        exit 1
elif test $# -eq 2
then
	if test -d $1 && echo "$2" | grep -E -o "^[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+$" >/dev/null
	then
		echo "Usuario: `whoami`" | tee -a /var/log/ipLog.log
		echo "Fecha y hora: `date`" | tee -a /var/log/ipLog.log
		echo "Version: `bash --version | head -1`" | tee -a /var/log/ipLog.log
		funcion2 $1 $2
	else
		echo "Error. Uso: \$path \$IP"
		exit 2
	fi
elif test $# -eq 1
then
	if test -d $1
	then
                echo "Usuario: `whoami`" | tee -a /var/log/ipLog.log
                echo "Fecha y hora: `date`" | tee -a /var/log/ipLog.log
                echo "Version: `bash --version | head -1`" | tee -a /var/log/ipLog.log
		funcionPath $1
	elif echo "$1" | grep -E -o "^[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+$" >/dev/null
	then
                echo "Usuario: `whoami`" | tee -a /var/log/ipLog.log
                echo "Fecha y hora: `date`" | tee -a /var/log/ipLog.log
                echo "Version: `bash --version | head -1`" | tee -a /var/log/ipLog.log
		funcionIP $1
	else
		echo "Error. Introduce un path o IP o ambas"
		exit 3
	fi
else
                echo "Usuario: `whoami`" | tee -a /var/log/ipLog.log
                echo "Fecha y hora: `date`" | tee -a /var/log/ipLog.log
                echo "Version: `bash --version | head -1`" | tee -a /var/log/ipLog.log
                echo
	funcion0
fi
