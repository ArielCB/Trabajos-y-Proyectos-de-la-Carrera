# -*- coding: utf-8 -*-
"""
Created on Wed May 24 18:58:04 2023

@author: a.carnes.2021
"""

#Funcion para multiplicar un vector v por un entero x
def multiplicacionV(x,v):
    n = len(v)
    u = [None]*n
    
    for i in range(0,n):
        u[i] = v[i]*x
    
    return u

#Funcion para multiplicar dos matrices
def multiplicacionMM(A,B):
    na = len(A)
    ma = len(A[0])
    mb = len(B[0])
    C = [None]*na
    
    for i in range(0,na):
        C[i] = [None]*mb
    
    for i in range(0,na):
        for j in range(0,mb):
            suma = 0
            for k in range(0,ma):
                suma += A[i][k]*B[k][j]
            C[i][j] = suma
    
    return C

#Funcion para multiplicar una matriz por un vector
def multiplicacionMV(A,v):
    n = len(A)
    m = len(v)
    u = [None]*n
    
    for i in range(0,n):
        suma = 0
        for j in range(0,m):
            suma += v[j]*A[i][j]
        u[i] = suma
    
    return u

#Funcion que calcula la resta de vectores
def restaV(v,u):
    n = len(v)
    w = [None]*n
    
    for i in range(0,n):
        w[i] = v[i]-u[i]
    
    return w

#Funcion que calcula la traspuesta de una matriz
def traspuesta(A):
    n = len(A)
    m = len(A[0])
    At = [None]*m
    
    for i in range(0,m):
        At[i] = [None]*n
    
    n = len(A)
    m = len(A[0])
    At = [None]*m
    
    for i in range(0,m):
        At[i] = [None]*n
    
    for i in range(0,n):
        for j in range(0,m):
            At[j][i] = A[i][j]
    
    return At

#Funcion que calcula el modulo de un vector
def modulo(v):
    n = len(v)
    
    suma = 0
    for i in range(0,n):
        suma += v[i]**2
    
    return suma**0.5

#Funcion que calcula el gradiente del sistema de ecuaciones
def gradiente(A,x,b):
    At = traspuesta(A)
    
    #Caluculo 2AtAx
    AtA = multiplicacionMM(At,A)
    Ax = multiplicacionMV(AtA,x)
    a2 = multiplicacionV(2,Ax)
    
    #Calculo 2Atb
    Ab = multiplicacionMV(At,b)
    b2 = multiplicacionV(2,Ab)
    
    #Resto los resultados y devuelvo el gradiente
    sol = restaV(a2,b2)
    return sol

#Funcion que aplica el algoritmo voraz de descenso de gradiente para aproximarse a una solucion
def descenso_gradiente(A,x,b,a,E):
    g = gradiente(A, x, b)
    gmod = modulo(g)
    
    #Bucle que itera la x hasta que el modulo del gradiente es suficientemente pequeño
    while(gmod>E):
        g = multiplicacionV(a,g) #Calculo de alfa por el gradiente
        x = restaV(x,g) #Calculo de la siguiente x de la iteracion
        g = gradiente(A,x,b) #Gradiente de la nueva x
        gmod = modulo(g) #modulo del nuevo gradiente
    
    return x


def lee_lista(n):
    a = []
    if n>0:
        cadenaEntrada = input()
        for i in range(0, n): 
            elemento = float(cadenaEntrada.split(" ")[i])
            a.append(elemento)
            
    return a


def imprime_lista(a):
    n = len(a)
    if n==0:
        pass
    elif n==1:
        print('%.4f'%a[0])
    else:
        print('%.4f'%a[0],end='')
        print(' ',end='')
        imprime_lista(a[1:])


cadenaEntrada=input()
n = int(cadenaEntrada.split(" ")[0])
m = int(cadenaEntrada.split(" ")[1])

A = [None]*n
for i in range(0,n):
    A[i] = lee_lista(m)

b = lee_lista(n)
x = lee_lista(m)
a = float(input())
E = float(input())

imprime_lista(descenso_gradiente(A,x,b,a,E))


