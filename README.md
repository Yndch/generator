# generator

 Entry.java:
 Класс состоит из полей номера, даты и имени, а также конструктора для маппинга объекта этого класса, нескольких сеттеров и метода readTsv, читающего tsv с помощью библиотеки univocity tsv parser и возвращающего список объектов класса Entry.
 
 Settings.java
 Класс состоит из классов Page с полями width и height (и геттеров для них) и класса Column с полями title и width. Экземпляр класса Page содержит в себе данных о ширине и высоте страницы, а список экзепляров класса columns содержит информацию о ширине каждого из элементов таблицы.
 Settings имеет публичный статический метод create, который открывает xml-файл с настройками, читает его построчно, а затем с помощью класса XmlMapper библиотеки com.fasterxml.jackson.dataformat.xml задает полям класса Settings значения из файла и возвращает в Main объект этого класса.
 Также он содержит методы getPageWidth, getPageHeight, getColumnWidth, используемые в косметических целях для удобства чтения кода.
 
Класс Main
  Метод printHead выводит шапку таблицы с помощью потока charOutput.
  Метод printNewPage вызывается тогда, когда существует необходимость завершить заполнение страницы таблицы, ввести символ новой страницы, напечатать новую шапку.
  Перегруженный метод writeHead осуществляет вывод шапки таблицы. Один из них, получающий экземпляр класса Settings, передает во второй данные полей этого класса, а второй передает в метод printHead готовую строку шапки.
  Перегруженный метод writeEntry осуществляет вывод одной строки таблицы и имеет ту же самую логику, что и writeHead (один из них принимает экзепляр класса Settings и передает в другой его данные, а второй возвращает в метод Main готовую строку таблицы)
  Метод fillEnoughSpace используется для добавления пустой строки в соответствующий список, если номер интерации цикла больше, чем количество доступных элементов списка. Это было нужно для того, чтобы в метод writeEntry передавались корректные данные по итератору (использование цепочки if-else мне показалось менее элегантным)
  Метод correctSplit принимает строку (имени или даты) и осуществяет необходимые изменения строки, подгоняя ее под ширину соответствующего поля в следующем порядке:
    1)Сначала с помощью метода split(" ") строка делится на массив строк, которые были разделены пробелами и, если этого достаточно, добавляет в список finalList элемент этого массива, подходящий по ширине
    2)Если разделения по пробелам недостаточно, используется split("(?=[^A-Za-zа-яА-Я0-9'])"), который разделяет строку согласно описанным в задании правилам, и, если этого достаточно, добавляет в список finalList элементы полученного массива
    3)Если и этого недостаточно, последний блок цикла делит строку по заданной ширине и добавляет в список finalList разделенные строки
  Следующий цикл реализует необходимые правки для того, чтобы соединить соседние элементы списка, если они умещаются в поле таблицы заданной ширины.
  Далее список возвращается к метод Main, в цикл for each для элементов списка класса Entry

  Использование множества вышеупомянутых методов не являлось необходимым для функциональности программы, но значительно увеличило читабельность кода.
  
  Метод Main создает и открывает необходимые для работы программы файлы и потоки, создает список entries, состоящий из экземпляров класса Entry, затем задает параметр currentHeight равный трем (он нужен для подсчета номера строки на странице), печатает шапку и начитает цикл for each для элементов списка entries, в котором и выполняется большая часть логики программы.
  
  
Примечательные моменты:
1)Если элемент таблицы не умешается на нынешней странице целиком, печатается новая страница и этот элемент выводится уже на него
2)Метод correctSplit не используется для корректировки под заданную ширину шапки, а также значений номера записи (как оказалось, это частично противоречит пункту м задания)
3)Разделитель переносится на следующую строку вместе со следующим значением
4)Отделяюшая строка печатается всегда
5)
