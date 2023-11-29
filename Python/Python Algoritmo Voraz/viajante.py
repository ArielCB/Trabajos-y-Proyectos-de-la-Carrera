# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""
#Funcion que calcula la distancia entre dos puntos
def distancia(x1,y1,x2,y2):
    x = (x1-x2)**2
    y = (y1-y2)**2
    sol = (x+y)**0.5
    return sol

def matriz_distancias(x,y):
    ln = len(x)
    md = [None]*ln #matriz de distancias
    for i in range(0,ln):
        md[i] = [None]*ln
    lv = [False]*ln #lista que guarda los puntos que se han vistitado
    #El punto 0 se marca como visitado porque se empieza por el
    lv[0] = True
    sol = [None]*ln
    
    #guardamos las distancias en la matriz de distancias md
    for i in range(0,ln):
        for j in range(0,ln):
            md[i][j]=distancia(x[i],y[i],x[j],y[j])
    
    dist = calcular_ruta(sol,0,md,lv)
    print('%.4f'%dist)
    imprime_lista(sol)

#El punto en el que se encuentra la funcion es i
#K indica el numero de puntos por los que se ha pasado
#mn es la distancia de la menor ruta encontrada, empieza en -1
#da es la distancia recorrida anteriormente
def calcular_ruta(sol,i,md,lv,k = 1,mn = -1, da = 0):
    ln = len(sol)
    
    #Si ya hemos visitado todos los puntos(k=numero de puntos)
    if k>=ln:
        #Se devuelve la distancia de la ruta(da+distancia del ultimo punto a 0)
        return da+md[i][0]
    
    else:
        
        #Si es la primera iteracion
        if i==0:
            # 0 es el primer punto visitado
            sol[0] = 0
            
            #mn sera el primer camino que recorramos para darle un valor
            lv[k] = True
            mn = calcular_ruta(sol,k,md,lv,k+1,mn,md[0][k])
            lv[k] = False
            sol[k] = k #Como es el primer camino marcamos que este siguiente punto es el segundo
            
            #Empezamos por el punto 2, los otros ya los hemos mirado
            for j in range(2,ln):
                dr = md[0][j] #distancia recorrida
                
                #Sabremos que no es un camino optimo si dr>mn por lo que no hara falta recorrerllo
                if dr<mn:
                    lv[j] = True
                    #La distancia recorrida se pasa como da
                    dist = calcular_ruta(sol,j,md,lv,k+1,mn,dr)
                    lv[j] = False
                    
                    #Si el camino recorrido en esta ruta es menor que el minimo se establece como nueva solucion(mn y orden de los puntos)
                    if dist<mn:
                        mn = dist 
                        sol[k] = j 
            return mn
        
        #Si no es la primera iteracion
        else:
            for j in range(1,ln):
                
                #Comprueba si ha sido visitado y calcula la distancia recorrida
                if not lv[j]:
                    dr = da+md[i][j]
                    
                    #Solo va a visitarlo si es la primera ruta o si la distancia recorrida es menor que la ruta optima
                    if dr<mn or mn==-1:
                        lv[j] = True
                        dist = calcular_ruta(sol,j,md,lv,k+1,mn,da+md[i][j])
                        lv[j] = False
                        
                        #Si la nueva ruta es mejor cambia la solucion(Ruta minima y orden de los puntos)
                        if dist<mn:
                            sol[k] = j
                            mn = dist
                        
                        #Si es la primera ruta la guarda como solucion
                        elif mn==-1:
                            sol[k] = j
                            mn = dist
            return mn

def imprime_lista(a):
    n = len(a)
    if n==0:
        pass
    elif n==1:
        print(a[0])
    else:
        print(a[0],end='')
        print(' ',end='')
        imprime_lista(a[1:])

n=int(input())
x = []
y = []

for i in range(0,n):
    cadenaEntrada = input()
    elemento = float(cadenaEntrada.split(" ")[0])
    x.append(elemento)
    elemento = float(cadenaEntrada.split(" ")[1])
    y.append(elemento)

matriz_distancias(x, y)


