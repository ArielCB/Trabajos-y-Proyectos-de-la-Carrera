import matplotlib.pyplot as plt

fig, ax = plt.subplots()

def CurvaH(orden,direccion='d',xo=0,yo=0):
    x=[]
    y=[]
    f=(x,y)
    if orden==1:
        if direccion=='d':
            x=[xo,xo,xo+1,xo+1]
            y=[yo,yo-1,yo-1,yo]
            f=(x,y)
            return f
        if direccion=='r':
            x=[xo,xo+1,xo+1,xo]
            y=[yo,yo,yo-1,yo-1]
            f=(x,y)
            return f
        if direccion=='u':
            x=[xo,xo,xo-1,xo-1]
            y=[yo,yo+1,yo+1,yo]
            f=(x,y)
            return f
        x=[xo,xo-1,xo-1,xo]
        y=[yo,yo,yo+1,yo+1]
        f=(x,y)
        return f
    else:
        if direccion=='d':
            
            curva1=CurvaH(orden-1,'r',xo,yo)
            f=curva1
            
            n=len(f[0])
            xs=f[0][n-1]
            ys=f[1][n-1]-1
            
            curva2=CurvaH(orden-1,'d',xs,ys)
            for i in range(0,n):
                f[0].append(curva2[0][i])
                f[1].append(curva2[1][i])
            
            xs=curva2[0][n-1]+1
            ys=curva2[1][n-1]
            
            curva3=CurvaH(orden-1,'d',xs,ys)
            for i in range(0,n):
                f[0].append(curva3[0][i])
                f[1].append(curva3[1][i])
            
            xs=curva3[0][n-1]
            ys=curva3[1][n-1]+1
            
            curva4=CurvaH(orden-1,'l',xs,ys)
            for i in range(0,n):
                f[0].append(curva4[0][i])
                f[1].append(curva4[1][i])
            
            return f
        
        if direccion=='r':
            
            curva1=CurvaH(orden-1,'d',xo,yo)
            f=curva1
            
            n=len(f[0])
            xs=f[0][n-1]+1
            ys=f[1][n-1]
            
            curva2=CurvaH(orden-1,'r',xs,ys)
            for i in range(0,n):
                f[0].append(curva2[0][i])
                f[1].append(curva2[1][i])
            
            xs=curva2[0][n-1]
            ys=curva2[1][n-1]-1
            
            curva3=CurvaH(orden-1,'r',xs,ys)
            for i in range(0,n):
                f[0].append(curva3[0][i])
                f[1].append(curva3[1][i])
            
            xs=curva3[0][n-1]-1
            ys=curva3[1][n-1]
            
            curva4=CurvaH(orden-1,'u',xs,ys)
            for i in range(0,n):
                f[0].append(curva4[0][i])
                f[1].append(curva4[1][i])
            
            return f
        
        if direccion=='u':
            
            curva1=CurvaH(orden-1,'l',xo,yo)
            f=curva1
            
            n=len(f[0])
            xs=f[0][n-1]
            ys=f[1][n-1]+1
            
            curva2=CurvaH(orden-1,'u',xs,ys)
            for i in range(0,n):
                f[0].append(curva2[0][i])
                f[1].append(curva2[1][i])
            
            xs=curva2[0][n-1]-1
            ys=curva2[1][n-1]
            
            curva3=CurvaH(orden-1,'u',xs,ys)
            for i in range(0,n):
                f[0].append(curva3[0][i])
                f[1].append(curva3[1][i])
            
            xs=curva3[0][n-1]
            ys=curva3[1][n-1]-1
            
            curva4=CurvaH(orden-1,'r',xs,ys)
            for i in range(0,n):
                f[0].append(curva4[0][i])
                f[1].append(curva4[1][i])
            
            return f
        
        curva1=CurvaH(orden-1,'u',xo,yo)
        f=curva1
        
        n=len(f[0])
        xs=f[0][n-1]-1
        ys=f[1][n-1]
        
        curva2=CurvaH(orden-1,'l',xs,ys)
        for i in range(0,n):
            f[0].append(curva2[0][i])
            f[1].append(curva2[1][i])
        
        xs=curva2[0][n-1]
        ys=curva2[1][n-1]+1
        
        curva3=CurvaH(orden-1,'l',xs,ys)
        for i in range(0,n):
            f[0].append(curva3[0][i])
            f[1].append(curva3[1][i])
        
        xs=curva3[0][n-1]+1
        ys=curva3[1][n-1]
        
        curva4=CurvaH(orden-1,'d',xs,ys)
        for i in range(0,n):
            f[0].append(curva4[0][i])
            f[1].append(curva4[1][i])
        
        return f
            

orden=int(input())
curva=CurvaH(orden)
ax.plot(curva[0],curva[1])
plt.show()