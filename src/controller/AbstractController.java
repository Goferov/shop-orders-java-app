package controller;

import model.AbstractModel;
import util.FileUtil;
import util.ValidatorUtil;
import view.AbstractFormView;
import view.AbstractView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public abstract class AbstractController<T extends AbstractModel, TView extends AbstractView, TForm extends AbstractFormView> {
    protected final TView view;
    protected final TForm form;
    private final String dataFile;
    private List<T> dataList;
    private static int currentId = 0;
    protected boolean isValidate = true;
    protected StringBuilder errorMsg = new StringBuilder();

    protected AbstractController(TView view, TForm form, String dataFile) {
        this.view = view;
        this.form = form;
        this.dataFile = dataFile;
        loadFromFile();
        view.addButtonAction(e -> showForm());
        view.removeButtonAction(e -> remove());
        form.submitForm(e -> add());
        form.cancelForm(e -> cancelForm());

        view.doubleClickAction(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Integer selectedRow = view.getSelectedElement();
                    showDetails(findById(selectedRow));
                }
            }
        });
    }

    private void loadFromFile() {
        dataList = FileUtil.loadFromFile(dataFile);
        for(T element : dataList) {
            view.addToView(element);
        }
    }

    protected int generateNextId() {
        int maxId = dataList.stream().mapToInt(T::getId).max().orElse(0);
        currentId = Math.max(currentId, maxId);
        return currentId + 1;
    }

    protected T findById(Integer id) {
        Optional<T> findedElem = dataList.stream().filter(c -> c.getId().equals(id)).findFirst();
        return findedElem.orElse(null);
    }

    protected abstract void showDetails(T element);
    protected abstract T create();
    protected abstract void validateFormFields();

    private void saveToFile() {
        FileUtil.saveToFile(dataFile, dataList);
    }

    private void showForm() {
        form.setVisible(true);
    }

    private boolean isValidate() {
        validateFormFields();
        if(!errorMsg.isEmpty()) {
            JOptionPane.showMessageDialog(this.form, errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void add() {
        if(isValidate()) {
            T element = create();
            dataList.add(element);
            view.addToView(element);
            saveToFile();
            form.clearFormFields();
            form.setVisible(false);
        }
        errorMsg.setLength(0);
        isValidate = true;

    }

    public void remove() {
        Integer selectedCustomer = view.getSelectedElement();
        if (selectedCustomer != null) {
            dataList.removeIf(c -> c.getId().equals(selectedCustomer));
            view.removeFromView(selectedCustomer);
            saveToFile();
        }
    }


    protected void validateAndColorField(JTextField field, String errorMessage) {
        if (!ValidatorUtil.validateTextField(field.getText())) {
            errorMsg.append(errorMessage);
            field.setBackground(Color.PINK);
        } else {
            field.setBackground(Color.WHITE);
        }
    }

    private void cancelForm() {
        form.setVisible(false);
    }

}
