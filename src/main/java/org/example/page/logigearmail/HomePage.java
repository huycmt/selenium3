package org.example.page.logigearmail;

import lombok.SneakyThrows;
import org.example.data.logigearmail.EmailData;
import org.example.element.Element;
import org.example.utils.FileUtils;
import org.example.utils.WebUtils;

import java.io.File;
import java.time.Duration;
import java.util.Objects;

public class HomePage {

    /**
     * Create email with info
     *
     * @param emailData
     */
    public void creatEmail(EmailData emailData) {
        homePageWindow = WebUtils.getWindowHandle();
        clickNew();
        WebUtils.waitForPageLoad();
        WebUtils.switchToPage(WebUtils.getWindowHandles().size() - 1);
        WebUtils.waitForPageLoad();
        fillEmailData(emailData);
    }

    /**
     * Save and close email popup
     */
    public void saveAndCloseEmailPopup(EmailData emailData) {
        saveButton.click();
        WebUtils.waitForTitleContain(emailData.getSubject());
        WebUtils.closePage(WebUtils.getWindowHandle());
        WebUtils.switchToPage(homePageWindow);
    }

    /**
     * Click Send Email button
     */
    public void sendEmail() {
        sendButton.click();
        WebUtils.switchToPage(homePageWindow);
    }

    /**
     * Fill data into email dialog
     *
     * @param emailData
     */
    public void fillEmailData(EmailData emailData) {
        if (Objects.nonNull(emailData.getTo())) {
            toTextBox.enter(emailData.getTo());
        }
        if (Objects.nonNull(emailData.getCc())) {
            ccTextBox.enter(emailData.getCc());
        }
        if (Objects.nonNull(emailData.getSubject())) {
            subjectTextBox.enter(emailData.getSubject());
        }
        if (Objects.nonNull(emailData.getContent())) {
            WebUtils.switchToFrame(iframe);
            contentTextArea.enter(emailData.getContent());
            WebUtils.switchToMain();
        }
        if (Objects.nonNull(emailData.getAttachment())) {
            attachFileButton.click();
            WebUtils.waitForPageLoad();
            WebUtils.switchToFrame(modalIframe);
            WebUtils.switchToFrame(attachIframe);
            chooseFile.uploadFile(new File(emailData.getAttachment()));
            attachButton.click();
            WebUtils.switchToMain();
        }
        if (Objects.nonNull(emailData.getInsertionImage())) {
            insertImageButton.click();
            WebUtils.waitForPageLoad();
            WebUtils.switchToFrame(modalIframe);
            WebUtils.switchToFrame(attachIframe);
            chooseFile.uploadFile(new File(emailData.getInsertionImage()));
            attachButton.click();
            WebUtils.switchToMain();
        }
    }

    /**
     * Click New button
     */
    public void clickNew() {
        newButton.click();
    }

    /**
     * Click Draft button
     */
    public void clickDraft() {
        draft.click();
        loading.waitForInvisible();
    }

    /**
     * Click on Subject in Subject list
     */
    public void clickOnSubject(String subject) {
        subjectItem.set(subject);
        subjectItem.waitForExist();
        subjectItem.click();
    }

    /**
     * Get email data in email preview
     */
    public EmailData getEmailData() {
        return EmailData.builder()
                .subject(subjectHeader.getText())
                .to(toText.getAttribute("title"))
                .content(contentText.getText())
                .attachment(attachmentName.isDisplayed(Duration.ZERO) ? attachmentName.getAttribute("title") : null)
                .build();
    }

    /**
     * Download image in email content
     *
     * @param path Path to save image
     */
    @SneakyThrows
    public void downloadImage(String path) {
        FileUtils.download(imgInContent.getAttribute("src"), path, true);
    }

    String homePageWindow;

    Element newButton = new Element("xpath=//span[.='New']", true);
    Element toTextBox = new Element("id=divTo", true);
    Element ccTextBox = new Element("id=divCc", true);
    Element subjectTextBox = new Element("id=txtSubj", true);
    Element contentTextArea = new Element("xpath=//body[@fpstyle='1']", true);
    Element saveButton = new Element("id=save", true);
    Element insertImageButton = new Element("id=insertimage", true);
    Element attachFileButton = new Element("id=attachfile", true);
    Element chooseFile = new Element("id=file1", true);
    Element attachButton = new Element("id=btnAttch", true);
    Element iframe = new Element("id=ifBdy", true);
    Element attachIframe = new Element("xpath=//iframe[contains(@src, 'AttachFileDialog')]", true);
    Element modalIframe = new Element("id=iFrameModalDlg", true);
    Element draft = new Element("xpath=//span[@fldrnm='Drafts']", true);
    Element loading = new Element("xpath=//div[.='Loading...']", true);
    Element subjectItem = new Element("xpath=//div[@id='divSubject' and .='%s']", true);
    Element subjectHeader = new Element("xpath=//div[@_pagetype='MessageViewSubPage' and not(contains(@style,'none'))]//div[@id='divConvTopic']", true);
    Element toText = new Element("xpath=//div[@_pagetype='MessageViewSubPage' and not(contains(@style,'none'))]//div[@id='divTo']/span", true);
    Element contentText = new Element("xpath=//div[@_pagetype='MessageViewSubPage' and not(contains(@style,'none'))]//div[@id='divBdy']//span", true);
    Element attachmentName = new Element("xpath=//div[@_pagetype='MessageViewSubPage' and not(contains(@style,'none'))]//a[@id='lnkAtmt']", true);
    Element imgInContent = new Element("xpath=//div[@_pagetype='MessageViewSubPage' and not(contains(@style,'none'))]//div[@id='divBdy']//img", true);
    Element sendButton = new Element("id=send", true);
}
