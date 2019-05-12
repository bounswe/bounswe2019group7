from django.test import SimpleTestCase
from django.test import Client, TestCase
from django.urls import reverse, resolve
from .views import *


class EventTest(TestCase):

    def __init__(self, *args, **kwargs):
        TestCase.__init__(self, *args, **kwargs)
        self.client = Client()

    def test_list(self):
        response = self.client.get(reverse(EventViewSet.list))
        self.assertEqual(response.status_code, 200)


class TestUrls(SimpleTestCase):

    def test_list_url(self):
        url = reverse('list')
        self.assertEquals(resolve(url).func, list)
