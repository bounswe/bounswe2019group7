from django.test import TestCase, Client
from django.urls import reverse
from .views import *


class CurrencyTest(TestCase):

    def __init__(self, *args, **kwargs):
        TestCase.__init__(self, *args, **kwargs)
        self.client = Client()

    def setUp(self):
        Currency.objects.create(from_currency='USD', to_currency='TRY', exchange_rate=5.93, id=0)

    def test_list(self):
        response = self.client.get(reverse(CurrencyViewSet.list))
        self.assertEqual(response.status_code, 200)

    def test_retrieve(self):
        queryset = Currency.objects.all()
        print(queryset)
        currency = get_object_or_404(queryset, id=0)
        serializer = CurrencySerializer(currency)
        response = self.client.get(reverse(CurrencyViewSet.retrieve, kwargs={'pk': 0}))
        self.assertEqual(response.data, serializer.data)
        self.assertTrue(response.content)

    def test_convert(self):
        response = self.client.get(reverse(CurrencyViewSet.list))
        self.assertEqual(response.status_code, 200)