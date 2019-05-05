from django.test import SimpleTestCase
from django.urls import reverse, resolve
from addToMailList.views import email_list_signup

class TestUrls(SimpleTestCase):

    def test_url_is_resolved(self):
        url = reverse('email-list-signup')
        self.assertEquals(resolve(url).func, email_list_signup)
