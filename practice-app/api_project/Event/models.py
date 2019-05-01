from django.db import models

class Event(models.Model):
	event_text = models.TextField(max_length=250)
	event_date = models.DateTimeField()
    
	def __str__(self):
		return self.event_text