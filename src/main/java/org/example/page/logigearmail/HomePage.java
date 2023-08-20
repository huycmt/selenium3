package org.example.page.logigearmail;

import io.qameta.allure.Step;
import org.example.data.logigearmail.EmailData;
import org.example.element.Element;
import org.example.utils.Constants;
import org.example.utils.WebUtils;

import java.io.File;
import java.util.Objects;

public class HomePage {

    @Step("Compose new email with attachment. Save the email and close the composing email pop up")
    public void creatEmailToDraft(EmailData emailData) {
        String currentPage = WebUtils.getWindowHandle();
        clickNew();
        WebUtils.waitForPageLoad();
        WebUtils.switchToPage(WebUtils.getWindowHandles().size() - 1);
        WebUtils.waitForPageLoad();
        fillEmailData(emailData);
        clickSaveButton();
        WebUtils.waitForTitleContain(emailData.getSubject());
        WebUtils.closePage(WebUtils.getWindowHandle());
        WebUtils.switchToPage(currentPage);
    }

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
    }

    public void clickNew() {
        newButton.click();
    }

    public void clickSaveButton() {
        saveButton.click();
    }

    @Step("Click Draft folder in left menu")
    public void clickDraft() {
        draft.click();
        loading.waitForInvisible();
    }

    @Step("Click on Subject")
    public void clickOnSubject(String subject) {
        subjectItem.set(subject);
        subjectItem.click();
    }

    @Step("Get email data")
    public EmailData getEmailData() {
        return EmailData.builder()
                .subject(subjectHeader.getText())
                .to(toText.getAttribute("title"))
                .content(contentText.getText())
                .attachment(attachmentName.getAttribute("title"))
                .build();
    }

    Element newButton = new Element("xpath=//span[.='New']", true);
    Element toTextBox = new Element("id=divTo", true);
    Element ccTextBox = new Element("id=divCc", true);
    Element subjectTextBox = new Element("id=txtSubj", true);
    Element contentTextArea = new Element("xpath=//body[@fpstyle='1']", true);
    Element saveButton = new Element("id=save", true);
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
    Element attachmentName = new Element("id=lnkAtmt", true);
}
