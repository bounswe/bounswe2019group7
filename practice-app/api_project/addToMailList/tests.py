from django.test import SimpleTestCase
from django.urls import reverse, resolve
from addToMailList.views import email_list_signup
from addToMailList.forms import EmailSignupForm


# tests if the url works fine.
class TestUrls(SimpleTestCase):

    def test_url_is_resolved(self):
        url = reverse('email-list-signup')
        self.assertEquals(resolve(url).func, email_list_signup)


# tests if form data is valid
class TestForm1(SimpleTestCase):

    def test_form_is_valid(self):
        form = EmailSignupForm(data={
            'email': 'asdasd@gmail.com'
        })

        self.assertTrue(form.is_valid())


# tests if form data is valid
class TestForm2(SimpleTestCase):

    def test_form_is_valid(self):
        form = EmailSignupForm(data={
            'randomName': 'asdasd@gmail.com'
        })

        self.assertFalse(form.is_valid())


# tests if form data is valid
class TestForm3(SimpleTestCase):

    def test_form_is_valid(self):
        form = EmailSignupForm(data={
            'randomName': 'asdasd@gmail.com',
            'mail': 'sadsadsadsadsa'
        })

        self.assertFalse(form.is_valid())
