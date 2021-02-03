package sample;

public class Entry {
    public String name, notes;
    public String number;

    public Entry(){
        this.name = "";
        this.number = "";
        this.notes = "";
    }

    public Entry(String nameEntry, String numberEntry, String notesEntry) {
        this.name = nameEntry;
        this.number = numberEntry;
        this.notes = notesEntry;



    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}



