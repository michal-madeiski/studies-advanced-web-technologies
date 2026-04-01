def siems(*args, **kwargs):
    print([(k, w) for k, w in kwargs.items()] + [a for a in args])
    
def dodaj_element(element, lista = []):
    lista.append(element)
    return lista

def fibbonaci(n):
    fi = [0,1]
    [fi.append(fi[i] + fi[i+1]) for i in range(n - 2)]
    return fi





def czytaj_api(odpowiedz_api):
    try: 
        return odpowiedz_api["data"]["message"]["content"]
    except Exception as e:
        return e
        
def zliczSlowa(tekst: str):
    zdanie = tekst.lower().replace(".", "").replace("!", "")
    slowa = zdanie.strip().split()
    dictionary = {}
    for slowo in slowa:
        if slowo in dictionary:
            dictionary[slowo] += 1
        else:
            dictionary[slowo] = 1
    return dictionary


def wyczysc_i_pomnoz(brudne_dane):
    czyste_dane = []
    
    for i in brudne_dane:
        try:
            i = int(i)
            czyste_dane.append(i*2)
        except ValueError, TypeError:
            print(f"Zły typ: {i}")

    return czyste_dane


if (__name__ == "__main__"):
    siems("siemka", "naklejka", lol = "fajne", siems = "niefajne")
    print(dodaj_element(1))
    print(dodaj_element(2))
    print(fibbonaci(30))

    odpowiedz_api = {
        "status": 200,
        "data": {
            "id": 123,
            "message": {
                "role": "assistant",
                "content": "Cześć, w czym mogę pomóc?"
            }
        }
    }

    print("=" * 10 + " Zadanie 1 " + "=" * 10)
    print(czytaj_api({siems: "lol"}))

    tekst = "AI to przyszłość. ai jest super!"
    print(zliczSlowa(tekst))

    brudne_dane = [10, "20", "Nokia", 30, None, "40"]
    print(wyczysc_i_pomnoz(brudne_dane))

    nums = [3,2,2,3]
    val = 3
    [x for x in nums if x > val]
