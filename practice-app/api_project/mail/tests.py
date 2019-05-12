from django.test import SimpleTestCase
from django.urls import reverse, resolve
from mail.views import index

# Create your tests here.

#checks if url works fine.
class TestUrls(SimpleTestCase):

    def test_url_is_resolved(self):
        url = reverse('index')
        self.assertEquals(resolve(url).func, index)
