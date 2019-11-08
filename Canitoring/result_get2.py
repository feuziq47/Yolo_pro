from time import sleep
n=1;
evaluation_list=[0,0,0]
slep_time=2;


def eval_person(li):
    if 'person' in li:
        return 1
    else:
        return 0


def eval_cup(li):
    if 'cup' in li or 'wine glass' in li or 'bottle' in li:
        return 1
    else:
        return 0


def eval_etc(li):
    if 'mouse' in li or 'laptop' in li or 'book' in li or 'cell phone' in li or 'remote' in li or 'backpack' in li:
        return 1
    else:
        return 0




f=open("result.txt",'r')
lines=f.readlines()

list=[]
temp=[]
for line in lines:
    if 'left' in line:
        obj=line.split(":")[0]
        temp.append(obj)
    if 'Object' in line:
        list.append(temp)
        temp=[]
list=list[2:]
result=list[-1]

    
evaluation_list[0]=eval_person(result)
evaluation_list[1]=eval_cup(result)
evaluation_list[2]=eval_etc(result)


if evaluation_list==[0,0,1] or evaluation_list==[1,0,1] or evaluation_list==[1,0,0]:
        print("NOT ORDER")          
elif evaluation_list==[0,0,0]:
        print("EMPTY")
elif evaluation_list==[1,1,1] or evaluation_list==[1,1,0]:
        print("USING")
elif evaluation_list==[0,1,1] or evaluation_list==[0,0,1] or evaluation_list==[0,1,0]:
		print("occupied")
f.close()
n=n+1;
