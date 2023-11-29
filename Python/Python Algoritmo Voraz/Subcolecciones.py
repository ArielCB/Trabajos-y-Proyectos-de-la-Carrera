# -*- coding: utf-8 -*-
"""
Created on Sun May 21 14:00:09 2023

@author: a.carnes.2021
"""

#D es el divisor, empieza en -1
def subcolecciones(lista,m,d=-1):
    n=len(lista)
    c=0 # C es un contador que acumula el numero de subconjuntos que va descubriendo
    
    # Si se piden subconjuntos de un elemento
    if m==1:
        
        # si es la primera iteracion devuelve el numero de elementos de la lista
        if d==-1:
            return n
        
        # Si no es la primera iteracion cuenta cada elemento divisible por d y devuelve la cuenta
        else: 
            for i in range(0,n):
                if lista[i]%d==0:
                    c+=1
            return c
    
    # Si se piden subconjuntos de mas de un elemento
    else:
        
        #Si es la primera iteracion
        if d==-1:
            
            #Bucle en el que se selecciona a cada elemento como divisor
            for i in range(0,n):
                d=lista.pop(i) #Se selecciona un divisor y se saca de la lista
                j=i-1
                aux=[]
                
                #El bucle revisa que los elementos anteriores de la lista no sean iguales al divisor para no contar varias veces subconjuntos iguales
                while j>=0:
                    
                    #Si un elemento es igual lo saca y lo almacena en un vector auxiliar junto a su posicion
                    if lista[j]==d:
                        aux.append(lista.pop(j))
                        aux.append(j)
                    j-=1
                
                #Suma al contador el numero de subconjuntos posibles que encuentre
                c+=subcolecciones(lista, m-1, d)
                ln=len(aux)
                
                #Este bucle vuelve a insertar en su posicion los elementos sacados anteriormente
                while (ln>0):
                    pos=aux.pop()
                    lista.insert(pos,aux.pop())
                    ln-=2
                lista.insert(i,d) #Inserta el elemento que estaba usando como divisor de nuevo
                
            return c
        
        #Si no es la primera iteracion
        else:
            for i in range(0,n):
                if lista[i]%d==0:
                    #Para no contar subcolecciones iguales se pasa la lista con los siguientes elementos
                    c+=subcolecciones(lista[i+1:], m-1, d)
            return c
                

def lee_lista(n):
    a = []
    if n>0:
        cadenaEntrada = input()
        for i in range(0, n): 
            elemento = int(cadenaEntrada.split(" ")[i])
            a.append(elemento)
            
    return a

n=int(input())
lista=lee_lista(n)
m=int(input())

print(subcolecciones(lista,m))