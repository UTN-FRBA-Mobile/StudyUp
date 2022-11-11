package com.studyup.classes.NewTeam.tags

import com.studyup.api.Activity
import com.studyup.api.Tag

class TagRecycler(val tag: Tag?, val activity: Activity?, val isTag:Boolean=true, var isExpanded:Boolean = false, val isButton: Boolean=false, val isEmpty: Boolean = false)