package com.jprada.core.util.algorithm;

import java.util.Arrays;
import java.util.List;

import com.jprada.core.entity.Actor;

public final class RadixSort {

	public static int maxYPos(Actor[] arr) {
		Integer maxValue = 0;

		for (int i = 0; i < arr.length; i++) {
			if (arr[i].getCoordY() > maxValue) {
				maxValue = (int) arr[i].getCoordY();
			}
		}

		return String.valueOf(maxValue).length();
	}

	// -------------------------- STATIC METHODS --------------------------
	public static void radixSort(int[] arr, int bits) {
		int[] barr = new int[arr.length];
		int[] b_orig = barr;
		int rshift = 0;

		for (int mask = ~(-1 << bits); mask != 0; mask <<= bits, rshift += bits) {
			int[] cntarray = new int[1 << bits];

			for (int p = 0; p < arr.length; ++p) {
				int key = (arr[p] & mask) >> rshift;

				++cntarray[key];
			}

			for (int i = 1; i < cntarray.length; ++i) {
				cntarray[i] += cntarray[i - 1];
			}

			for (int p = arr.length - 1; p >= 0; --p) {
				int key = (arr[p] & mask) >> rshift;

				--cntarray[key];
				barr[cntarray[key]] = arr[p];
			}

			int[] temp = barr;

			barr = arr;
			arr = temp;
		}

		if (arr == b_orig) {
			System.arraycopy(arr, 0, barr, 0, arr.length);
		}
	}

	public static List<Actor> sortEntities(List<Actor> CharacterList) {
		Actor[] arr = CharacterList.toArray(new Actor[0]);
		int bits = maxYPos(arr);
		Actor[] earr = new Actor[arr.length];
		Actor[] earr_origi = earr;
		int rshift = 0;

		for (int mask = ~(-1 << bits); mask != 0; mask <<= bits, rshift += bits) {
			int[] cntarray = new int[1 << bits];

			for (int p = 0; p < arr.length; ++p) {
				int key = (((int) arr[p].getCoordY()) & mask) >> rshift;

				if (key < 0) {
					key = 0;
				}

				++cntarray[key];
			}

			for (int i = 1; i < cntarray.length; ++i) {
				cntarray[i] += cntarray[i - 1];
			}

			for (int p = arr.length - 1; p >= 0; --p) {
				int key = (((int) arr[p].getCoordY()) & mask) >> rshift;

				if (key < 0) {
					key = 0;
				}

				--cntarray[key];
				earr[cntarray[key]] = arr[p];
			}

			Actor[] temp = earr;

			earr = arr;
			arr = temp;
		}

		if (arr == earr_origi) {
			System.arraycopy(arr, 0, earr, 0, CharacterList.size());
		}

		return Arrays.asList(arr);
	}
}
