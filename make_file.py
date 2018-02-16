import random
f = open('D:\\SORT_THIS\\data\\decent.txt','w')

try:
    for i in range(1000000):
        f.write(str(random.randint(1,1000000000))+'\n')
finally:
    f.close()